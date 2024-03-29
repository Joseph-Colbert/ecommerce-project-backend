package com.josephcolbert.ecommerce.security.securityService;

import com.josephcolbert.ecommerce.dao.CustomerRepository;
import com.josephcolbert.ecommerce.entity.Customer;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class UserService {

    @Autowired
    CustomerRepository customerRepository;

    public Optional<Customer> getByUserNameOrEmail(String nameOrEmail) {
        return customerRepository.findByUserNameOrEmail(nameOrEmail,nameOrEmail);
    }

    public Optional<Customer> getByTokenPassword(String tokenPassword) {
        return customerRepository.findByTokenPassword(tokenPassword);
    }



    public boolean existsByUserName(String userName) {
        return customerRepository.existsByUserName(userName);
    }

    public boolean existsByEmail(String email) {
        return customerRepository.existsByEmail(email);
    }

    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

}
