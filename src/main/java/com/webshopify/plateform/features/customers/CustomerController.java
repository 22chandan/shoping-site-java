package com.webshopify.plateform.features.customers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.*;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.webshopify.plateform.features.customers.services.CustomerService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class CustomerController { // Fixed typo: was "CustomerControleer"
    
    @Autowired
    private CustomerService customerService;
    
    @GetMapping("/createaccount")
    public String getCreateAccount(Model model) {
        CustomerBean customer = new CustomerBean();
        model.addAttribute("customer", customer);
        return "sign-up";
    }

    @PostMapping("/createaccount")    
    public String createAccount(@Valid CustomerBean customer, BindingResult bindingResult, 
                              Model model, RedirectAttributes redirectAttributes) {
        
        if (bindingResult.hasErrors()) {
            List<String> errorsList = new ArrayList<>();
            bindingResult.getAllErrors().forEach(error -> {
                errorsList.add(error.getDefaultMessage());
            });
            model.addAttribute("errors", errorsList);
            model.addAttribute("customer", customer);
            return "sign-up";
        }
        
        try {
            customerService.SaveCustomer(customer);
            redirectAttributes.addFlashAttribute("message", 
                "Successfully Registered. <a href='/'>Click here to login</a>");
            return "redirect:/createaccount";
        } catch (Exception e) {
            model.addAttribute("error", "Registration failed. Please try again.");
            model.addAttribute("customer", customer);
            return "sign-up";
        }
    }
    
    @PostMapping("/login")
    public String loginUser(CustomerBean customer, Model model, HttpSession session) {
        // TODO: Implement proper authentication with password hashing
        // This is a simplified version - use Spring Security in production
        
        try {
            List<CustomerBean> allUsers = customerService.findAllCustomer();
            
            for (CustomerBean cust : allUsers) {
                if (cust.getUserName().equals(customer.getUserName()) && 
                    cust.getPassword().equals(customer.getPassword())) {
                    
                    // Store user info in session
                    session.setAttribute("loggedInUser", cust.getUserName());
                    session.setAttribute("customerId", cust.getCustomerId());
                    
                    model.addAttribute("user", customer.getUserName());
                    return "dashboard";
                }
            }
            
            model.addAttribute("message", "Invalid username or password");
            return "home";
            
        } catch (Exception e) {
            model.addAttribute("message", "Login failed. Please try again.");
            return "home";
        }
    }
    
    @GetMapping("/view-customer")
    public String getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "customerId") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir,
            Model model) {
        
        try {
            // Validate page and size parameters
            if (page < 0) page = 0;
            if (size <= 0) size = 10;
            if (size > 100) size = 100; // Limit max page size
            
            // Create sort object
            Sort sort = sortDir.equalsIgnoreCase("desc") ? 
                Sort.by(sortBy).descending() : 
                Sort.by(sortBy).ascending();
            
            Pageable pageable = PageRequest.of(page, size, sort);
            
            // Get paginated data from service
            Page<CustomerBean> customerPage = customerService.findAllCustomersPaginated(pageable);
            
            // Add attributes to model
            model.addAttribute("customerPage", customerPage);
            model.addAttribute("customers", customerPage.getContent());
            model.addAttribute("currentPage", page);
            model.addAttribute("pageSize", size);
            model.addAttribute("totalPages", customerPage.getTotalPages());
            model.addAttribute("totalElements", customerPage.getTotalElements());
            model.addAttribute("sortBy", sortBy);
            model.addAttribute("sortDir", sortDir);
            model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
            
            // Check if there are customers
            model.addAttribute("hasCustomers", !customerPage.isEmpty());
            
            // Add pagination helper attributes
            model.addAttribute("isFirst", customerPage.isFirst());
            model.addAttribute("isLast", customerPage.isLast());
            model.addAttribute("hasNext", customerPage.hasNext());
            model.addAttribute("hasPrevious", customerPage.hasPrevious());
            return "all_customer";
            
        } catch (Exception e) {
            model.addAttribute("error", "Failed to load customers");
            System.out.println("Got the Error bro thank you  so much");
            return "error";
        }
    }

    @GetMapping("/add-customer")
    public String addCustomerForm(Model model) {
        model.addAttribute("customer", new CustomerBean());
        return "add_customer";
    }
    
    @GetMapping("/edit-customer/{id}")
    public String editCustomer(@PathVariable("id") int id, Model model) {
        try {
            if (id <= 0) {
                model.addAttribute("error", "Invalid customer ID");
                return "redirect:/view-customer";
            }
            
            CustomerBean customer = customerService.findCustomerById(id);
            if (customer != null) {
                model.addAttribute("customer", customer);
            } else {
                model.addAttribute("error", "Customer not found");
                return "redirect:/view-customer";
            }
            
            return "add_customer";
            
        } catch (Exception e) {
            model.addAttribute("error", "Failed to load customer details");
            return "redirect:/view-customer";
        }
    }
    
    @PostMapping("/edit-customer/{id}")
    public String editCustomerDetails(@PathVariable("id") int id, 
                                    @Valid CustomerBean customer, 
                                    BindingResult bindingResult,
                                    Model model, 
                                    RedirectAttributes redirectAttributes) {
        
        if (bindingResult.hasErrors()) {
            model.addAttribute("customer", customer);
            return "add_customer";
        }
        
        try {
            if (id > 0) {
                customer.setCustomerId(id);
                customerService.updateCustomer(customer);
                redirectAttributes.addFlashAttribute("success", "Customer updated successfully");
            } else {
                redirectAttributes.addFlashAttribute("error", "Invalid customer ID");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to update customer");
        }
        
        return "redirect:/view-customer";
    }
    
    @PostMapping("/add-customer")
    public String addCustomerToDb(@Valid CustomerBean customer, 
                                BindingResult bindingResult,
                                Model model, 
                                RedirectAttributes redirectAttributes) {
        
        if (bindingResult.hasErrors()) {
            model.addAttribute("customer", customer);
            return "add_customer";
        }
        
        try {
            customerService.SaveCustomer(customer);
            redirectAttributes.addFlashAttribute("success", "Customer added successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to add customer");
        }
        
        return "redirect:/view-customer";
    }

    @GetMapping("/delete-customer/{id}")
    public String deleteCustomer(@PathVariable("id") int id, 
                               RedirectAttributes redirectAttributes) {
        try {
            if (id > 0) {
                customerService.deleteCustomer(id);
                redirectAttributes.addFlashAttribute("success", "Customer deleted successfully");
            } else {
                redirectAttributes.addFlashAttribute("error", "Invalid customer ID");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to delete customer");
        }
        
        return "redirect:/view-customer";
    }
    
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}