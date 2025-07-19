package com.webshopify.plateform.features.customers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.webshopify.plateform.features.customers.services.CustomerServiceImp;

import jakarta.validation.Valid;

@Controller
public class CustomerControleer {
	CustomerServiceImp  customerservice = new CustomerServiceImp();
    @GetMapping("/createaccount")
    public String getCreateAccount(Model model) {
    	CustomerBean customer = new CustomerBean();
		model.addAttribute("customer", customer);
        return "sign-up";
    }

    @PostMapping("/createaccount")	
    public String createAccount(@Valid CustomerBean customer,BindingResult bindingresult, Model model) {
    	List<String>errorslist=new ArrayList<String>();
    	if(bindingresult.hasErrors()) {
    		bindingresult.getAllErrors().forEach(error -> {
    		    errorslist.add(error.getDefaultMessage());
    		});
    		model.addAttribute("errors",errorslist);
    		model.addAttribute("customer", customer);
    		return "sign-up";
    	}
        customerservice.SaveCustomer(customer);
		model.addAttribute("customer", customer);
        model.addAttribute("message", "Successfully Registered. Click <a href='/'>here</a>");
        return "sign-up";  
    }
    @PostMapping("/login")
    public String loginuser(CustomerBean customer, Model model) {
//    	System.out.println("I am in login user "+customer.getUserName()+customer.getPassword());
    	List<CustomerBean> alluser = customerservice.findAllCustomer();
    	for(CustomerBean  cust  : alluser) {
    		if(cust.getUserName().equals(customer.getUserName())) {
    			if(cust.getPassword().equals(customer.getPassword())) {
    				System.out.println("I got it");
    				model.addAttribute("user", customer.getUserName());
//    				model.addAttribute("customerList)
    				return "dashboard.html";
    			}    		
    	    }
    	}
    		
    	model.addAttribute("message", "User Not found");
    	return "/home";
    }
    @GetMapping("/view-customer")
    public String getAlluser(Model model) {
    	List<CustomerBean> alluser = customerservice.findAllCustomer();
    	model.addAttribute("allUser", alluser);
    	return "../static/pages/data-tables.html";

    }
    @GetMapping("/add-customer")
    public String addcustomer(Model model) {
    	List<CustomerBean> alluser = customerservice.findAllCustomer();
    	model.addAttribute("allUser", alluser);
    	System.out.println("add customer");
    	return "add_customer.html";

    }
    
    @PostMapping("/add-customer")
    public String addcustometodb(CustomerBean customer,Model model) {
    	System.out.println("adding customer to db");
    	customerservice.SaveCustomer(customer);
    	return getAlluser(model);
//    	return "../static/pages/data-tables.html";
    }

    
}
