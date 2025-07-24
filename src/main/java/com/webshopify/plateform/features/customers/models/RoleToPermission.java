package com.webshopify.plateform.features.customers.models;

import java.io.Serializable;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

public class RoleToPermission implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6023590554774320389L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@ManyToOne
	private UserRole role;

	@ManyToOne
	private UserPermission userPermission;

}
