package com.josephcolbert.ecommerce.dao;

import com.josephcolbert.ecommerce.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Customer findByEmail(String theEmail); //select * from customer c where c.email = theEmail

    Optional<Customer> findByUserName(String userName);
    boolean existsByUserName(String userName);
    boolean existsByEmail(String email);
}
