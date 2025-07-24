package com.webshopify.plateform.features.customers.customvalidator;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CustomEmailDomainValidator implements ConstraintValidator<EmailDomainValidator, String> {
	private	static final Logger log = LoggerFactory.getLogger(CustomEmailDomainValidator.class);
	
	@Autowired
	private CustomerServiceUtils customerServiceUtils;
    @Override
    public void initialize(EmailDomainValidator constraintAnnotation) {
        // No initialization required for now
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
//    	return customerServiceUtils.isValidEmail(email);
    	return true;
    }
}
