package com.josephcolbert.ecommerce.security.securityService;

import com.josephcolbert.ecommerce.dao.CustomerRepository;
import com.josephcolbert.ecommerce.entity.Customer;
import com.josephcolbert.ecommerce.security.securityEntity.PrincipalUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserService customerRepository;

    @Override
    public UserDetails loadUserByUsername(String nameOrEmail) throws UsernameNotFoundException {
        Customer user = customerRepository.getByUserNameOrEmail(nameOrEmail).get();
        return PrincipalUser.build(user);
    }
}
