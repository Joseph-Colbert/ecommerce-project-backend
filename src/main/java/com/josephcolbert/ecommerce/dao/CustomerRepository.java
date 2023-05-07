package com.josephcolbert.ecommerce.dao;

import com.josephcolbert.ecommerce.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;


@RepositoryRestResource
@CrossOrigin("http://localhost:4200")
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Customer findByEmail(String theEmail); //select * from customer c where c.email = theEmail
}
