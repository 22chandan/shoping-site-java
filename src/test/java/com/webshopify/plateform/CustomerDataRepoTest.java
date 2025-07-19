package com.webshopify.plateform;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.webshopify.plateform.features.customers.models.Customer;
import com.webshopify.plateform.features.customers.repository.CustomerDataRepo;

public class CustomerDataRepoTest extends WeShopifyPlatform1ApplicationTests {
	@Autowired
	private CustomerDataRepo customerdatarepo;
	
	@Test
	public void testcustomerCreation() {
		Customer customer  = new Customer();
		customer.setEmail("itschandan26@gmail.com");
		customer.setUserName("Chandan Kumar");
		customer.setMobileNumber("7488152514");
		customer.setPassword("123445566");
		customer = customerdatarepo.save(customer);
		System.out.println("CustomerId is  :- "+ customer.getCustomerId());
		assertNotNull(customer.getCustomerId());
	}
	
}
