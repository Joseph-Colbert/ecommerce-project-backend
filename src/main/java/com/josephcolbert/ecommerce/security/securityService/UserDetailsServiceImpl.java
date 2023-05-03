package com.josephcolbert.ecommerce.security.securityService;

import com.josephcolbert.ecommerce.security.securityEntity.PrincipalUser;
import com.josephcolbert.ecommerce.security.securityEntity.User;
import com.josephcolbert.ecommerce.security.securityRepository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(userName).get();
        return PrincipalUser.build(user);
    }
}
