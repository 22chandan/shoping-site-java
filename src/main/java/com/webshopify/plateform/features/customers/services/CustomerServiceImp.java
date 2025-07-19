package com.webshopify.plateform.features.customers.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.webshopify.plateform.features.customers.CustomerBean;

public class CustomerServiceImp implements CustomerService {
	public static ArrayList<CustomerBean> IN_MEMORY_DATA = new ArrayList<>(); 

	@Override
	public CustomerBean SaveCustomer(CustomerBean customerbean) {
		int id = new Random().nextInt(); 
		customerbean.setCustomerId(id);
		IN_MEMORY_DATA.add(customerbean);
		return customerbean;
	}

	@Override
	public CustomerBean updateCustomer(CustomerBean customerbean) {
		List<CustomerBean> ls = deleteCustomer(customerbean.getCustomerId());
		IN_MEMORY_DATA.add(customerbean);
		return customerbean;
	}

	@Override
	public List<CustomerBean> findAllCustomer() {
		return IN_MEMORY_DATA;
	}

	@Override
	public CustomerBean findCustomerById(int CustomerId) {

		for(CustomerBean customer:IN_MEMORY_DATA) {
			if(customer.getCustomerId() == CustomerId) {
				return customer;
			}
		}
		return new CustomerBean();
	}

	@Override
	public List<CustomerBean> deleteCustomer(int customerid) {
		IN_MEMORY_DATA.remove(findCustomerById(customerid));
		return IN_MEMORY_DATA;
	}

	@Override
	public List<CustomerBean> searchCustomer() {
		return IN_MEMORY_DATA;
	}

}
