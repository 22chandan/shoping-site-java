package com.webshopify.plateform.features.customers.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.webshopify.plateform.features.customers.models.Customer;


public interface CustomerDataRepo extends JpaRepository<Customer, Integer> {

}
