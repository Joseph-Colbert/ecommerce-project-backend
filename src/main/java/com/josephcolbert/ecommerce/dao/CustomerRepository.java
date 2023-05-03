package com.josephcolbert.ecommerce.dao;

import com.josephcolbert.ecommerce.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Customer findByEmail(String theEmail); //select * from customer c where c.email = theEmail
}
