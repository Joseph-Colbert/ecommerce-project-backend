package com.josephcolbert.ecommerce.controller;

import com.josephcolbert.ecommerce.dao.CustomerRepository;
import com.josephcolbert.ecommerce.dao.PromotionRepository;
import com.josephcolbert.ecommerce.dto.Purchase;
import com.josephcolbert.ecommerce.dto.PurchaseResponse;
import com.josephcolbert.ecommerce.entity.Customer;
import com.josephcolbert.ecommerce.entity.Promotion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api")
public class CustomerController {
    private final CustomerRepository customerRepository;

    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @GetMapping("/customers/{userName}")
    public ResponseEntity<Customer> findByUserName(@PathVariable("userName") String userName) {
        Customer customer = customerRepository.findByUserName(userName);
        return new ResponseEntity<>(customer, null, HttpStatus.OK);
    }

   /* @GetMapping("/customers/{mail}")
    public Customer findByEmail(@PathVariable("mail") String mail) {
        Customer customer = customerRepository.findByEmail(mail);
        return customer;
    }*/

    // Promociones
    /*  @Autowired
    private PromotionRepository promotionRepository;

  @GetMapping("/promotions")
    public List<Promotion> getAllPromotions() {
        return promotionRepository.findAll();
    }

    @PostMapping
    public Promotion createPromotion(@RequestBody Promotion promotion) {
        return promotionRepository.save(promotion);
    }*/

    // Otros métodos como obtener una promoción por su ID, actualizar y eliminar una promoción
    // ...
}
