package com.webshopify.plateform.features.customers.models;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class UserPermission implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -877519278968154821L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	private String action;
	
	@OneToMany(mappedBy="role")
	List<RoleToPermission>actions;
}
