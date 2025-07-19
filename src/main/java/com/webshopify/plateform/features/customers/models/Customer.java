package com.webshopify.plateform.features.customers.models;

import java.io.Serializable;

import com.webshopify.plateform.features.customers.customvalidator.EmailDomainValidator;
import com.webshopify.plateform.features.customers.customvalidator.PasswordValidator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;


@Entity // Just like making it table means entitiy will make the RDBMS Table by using ORM (Hibernate)
@Table(name="Customer")  // Optional, it is not need if don't add it will automatically generated.
@Data
public class Customer implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(unique=true,nullable=false)
	private String email;
	
	private String mobileNumber;
	
	@Column(nullable =false,updatable=true)
	private String password;    
	
	private String userName;
	@Id //Just Like primary Key
	@GeneratedValue(strategy=GenerationType.AUTO) // we can use table or sequence but it can vary like some of database dpesnot support it so we can use auto 
	private int customerId;
}
