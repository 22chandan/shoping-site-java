package com.webshopify.plateform.features.customers;

import java.io.Serializable;

import com.webshopify.plateform.features.customers.customvalidator.EmailDomainValidator;
import com.webshopify.plateform.features.customers.customvalidator.PasswordValidator;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CustomerBean implements Serializable{
	@EmailDomainValidator(message="Email Is not Valid")
	private String email;
	@NotEmpty(message="Mobile number can't be empty")
	private String mobileNumber;
	@PasswordValidator(message="Password must contain at least one digit, one letter, 6–20 characters")
	private String password;    
	@NotEmpty(message="Username  eannot be empty")
	private String userName;
	private int customerId;
//	public String getEmail() {
//		return email;
//	}
//	public void setEmail(String email) {
//		this.email = email;
//	}
//	public String getMobileNumber() {
//		return mobileNumber;
//	}
//	public void setMobileNumber(String mobileNumber) {
//		this.mobileNumber = mobileNumber;
//	}
//	public String getPassword() {
//		return password;
//	}
//	public void setPassword(String password) {
//		this.password = password;
//	}
//	public String getUserName() {
//		return userName;
//	}
//	public void setUserName(String userName) {
//		this.userName = userName;
//	}
//	public int getCustomerId() {
//		return customerId;
//	}
//	public void setCustomerId(int customerId) {
//		this.customerId = customerId;
//	}
//		
}
