package com.webshopify.plateform;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;

import com.webshopify.plateform.features.customers.CustomerBean;
import com.webshopify.plateform.features.customers.services.CustomerService;

@TestMethodOrder(OrderAnnotation.class)
public class CustomerServiceTest extends WeShopifyPlatform1ApplicationTests {

    @Autowired
    private CustomerService customerService;

    // Shared across tests using order
    static int savedCustomerId;

    @Test
    @Order(1)
    public void testSaveCustomer() {
        CustomerBean customer = new CustomerBean();
        customer.setEmail("itschandan216@gmail.com");
        customer.setUserName("Chandan Kumar");
        customer.setMobileNumber("74881532514");
        customer.setPassword("1234455636");

        CustomerBean saved = customerService.SaveCustomer(customer);
        assertNotNull(saved);
        assertTrue(saved.getCustomerId() > 0);
        savedCustomerId = saved.getCustomerId(); // Store for other tests
        System.out.println("Saved ID: " + savedCustomerId);
    }

    @Test
    @Order(2)
    public void testUpdateCustomer() {
        CustomerBean existing = customerService.findCustomerById(savedCustomerId);
        assertNotNull(existing);

        existing.setPassword("UpdatedPassword123");
        CustomerBean updated = customerService.updateCustomer(existing);
        assertEquals("UpdatedPassword123", updated.getPassword());
    }

    @Test
    @Order(3)
    public void testFindById() {
        CustomerBean customer = customerService.findCustomerById(savedCustomerId);
        assertNotNull(customer);
        assertEquals(savedCustomerId, customer.getCustomerId());
    }

    @Test
    @Order(4)
    public void testFindAllCustomers() {
        List<CustomerBean> customers = customerService.findAllCustomer();
        assertNotNull(customers);
        assertTrue(customers.size() > 0);
    }

    @Test
    @Order(5)
    public void testDeleteCustomer() {
        customerService.deleteCustomer(savedCustomerId);
        CustomerBean deleted = customerService.findCustomerById(savedCustomerId);
        assertNull(deleted, "Customer should be null after deletion.");
    }
}
