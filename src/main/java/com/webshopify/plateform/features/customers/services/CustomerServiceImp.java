package com.webshopify.plateform.features.customers.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.webshopify.plateform.features.customers.CustomerBean;
import com.webshopify.plateform.features.customers.models.Customer;
import com.webshopify.plateform.features.customers.repository.CustomerDataRepo;

@Service
public class CustomerServiceImp implements CustomerService {

    @Autowired
    private CustomerDataRepo customerReposdata;

    public CustomerServiceImp(CustomerDataRepo customerReposdata) {
        this.customerReposdata = customerReposdata;
    }

    @Override
    public CustomerBean SaveCustomer(CustomerBean customerBean) {
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerBean, customer);
        customer = customerReposdata.save(customer); // Save returns entity with ID
        BeanUtils.copyProperties(customer, customerBean); // Copy ID back to bean
        return customerBean;
    }

    @Override
    public CustomerBean updateCustomer(CustomerBean customerBean) {
        if (customerBean.getCustomerId() == 0) {
            throw new IllegalArgumentException("Customer ID is required for update.");
        }

        Optional<Customer> optionalCustomer = customerReposdata.findById(customerBean.getCustomerId());
        if (!optionalCustomer.isPresent()) {
            throw new IllegalArgumentException("Customer not found with ID: " + customerBean.getCustomerId());
        }

        Customer customer = optionalCustomer.get();
        BeanUtils.copyProperties(customerBean, customer);
        customer = customerReposdata.save(customer);
        BeanUtils.copyProperties(customer, customerBean);
        return customerBean;
    }

    @Override
    public List<CustomerBean> findAllCustomer() {
        Iterable<Customer> customerEntities = customerReposdata.findAll();
        List<CustomerBean> customers = new ArrayList<>();

        for (Customer entity : customerEntities) {
            CustomerBean bean = new CustomerBean();
            BeanUtils.copyProperties(entity, bean);
            customers.add(bean);
        }

        return customers;
    }

    @Override
    public CustomerBean findCustomerById(int customerId) {
        Optional<Customer> optionalCustomer = customerReposdata.findById(customerId);
        if (!optionalCustomer.isPresent()) {
            return null; // or throw exception
        }

        CustomerBean bean = new CustomerBean();
        BeanUtils.copyProperties(optionalCustomer.get(), bean);
        return bean;
    }

    @Override
    public void deleteCustomer(int customerId) {
        if (customerReposdata.existsById(customerId)) {
            customerReposdata.deleteById(customerId);
        } else {
            throw new IllegalArgumentException("Customer not found with ID: " + customerId);
        }
    }
    

    @Override
    public List<CustomerBean> searchCustomer() {
	        // Placeholder for future search implementation
	        return new ArrayList<>();
    }
    @Override
    public Page<CustomerBean> findAllCustomersPaginated(Pageable pageable) {
        return customerReposdata.findAll(pageable).map(customer -> {
            CustomerBean bean = new CustomerBean();
            BeanUtils.copyProperties(customer, bean);
            return bean;
        });
    }
}
