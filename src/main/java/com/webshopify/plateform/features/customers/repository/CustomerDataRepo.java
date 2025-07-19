package com.webshopify.plateform.features.customers.repository;

import org.springframework.data.repository.CrudRepository;

import com.webshopify.plateform.features.customers.models.Customer;


public interface CustomerDataRepo extends CrudRepository<Customer, Integer> {

}
