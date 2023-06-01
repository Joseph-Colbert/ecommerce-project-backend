package com.josephcolbert.ecommerce.controller;

import com.josephcolbert.ecommerce.dao.CustomerRepository;
import com.josephcolbert.ecommerce.dto.Purchase;
import com.josephcolbert.ecommerce.dto.PurchaseResponse;
import com.josephcolbert.ecommerce.entity.Customer;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api")
public class CustomerController {
    private final CustomerRepository customerRepository;

    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @GetMapping("/customers/{userName}")
    public Customer findByUserName(@PathVariable("userName") String userName) {
        Customer customer = customerRepository.findByUserName(userName);
        return customer;
    }
}
