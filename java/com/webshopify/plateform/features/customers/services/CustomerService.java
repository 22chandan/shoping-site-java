package com.webshopify.plateform.features.customers.services;

import java.util.List;

import com.webshopify.plateform.features.customers.CustomerBean;

public interface CustomerService {
	public CustomerBean SaveCustomer(CustomerBean customerbean);
	public CustomerBean updateCustomer(CustomerBean customerbean);
	public List<CustomerBean> findAllCustomer();
	public CustomerBean findCustomerById(int CustomerId);
	public List<CustomerBean> deleteCustomer(int customerid);
	
	public List<CustomerBean> searchCustomer();
}
