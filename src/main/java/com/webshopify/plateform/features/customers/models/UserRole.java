package com.webshopify.plateform.features.customers.models;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class UserRole implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6141711762055596739L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	private String role;
	private String description;
}
