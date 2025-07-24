package com.webshopify.plateform.features.customers.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.webshopify.plateform.features.customers.CustomerBean;

public interface CustomerService {
	public CustomerBean SaveCustomer(CustomerBean customerbean);
	public CustomerBean updateCustomer(CustomerBean customerbean);
	public List<CustomerBean> findAllCustomer();
	public CustomerBean findCustomerById(int CustomerId);
	public void deleteCustomer(int customerid);
	public  Page<CustomerBean>  findAllCustomersPaginated(Pageable page);

	public List<CustomerBean> searchCustomer();
}
