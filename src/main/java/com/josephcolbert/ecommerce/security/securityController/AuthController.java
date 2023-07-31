package com.josephcolbert.ecommerce.security.securityController;

import com.josephcolbert.ecommerce.entity.Customer;
import com.josephcolbert.ecommerce.entity.Enterprise;
import com.josephcolbert.ecommerce.security.jwt.JwtProvider;
import com.josephcolbert.ecommerce.security.securityDto.*;
import com.josephcolbert.ecommerce.security.securityEntity.Rol;
import com.josephcolbert.ecommerce.security.securityEnums.RolName;
import com.josephcolbert.ecommerce.security.securityService.RolService;
import com.josephcolbert.ecommerce.security.securityService.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserService userService;

    @Autowired
    RolService rolService;

    @Autowired
    JwtProvider jwtProvider;

    @PostMapping("/nuevo")
    public ResponseEntity<?> nuevo(@Valid @RequestBody NewUser newUser, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return new ResponseEntity(new MessageDto("Campos mal ingresados o email inválido"), HttpStatus.BAD_REQUEST);
        if(userService.existsByUserName(newUser.getUserName()))
            return new ResponseEntity(new MessageDto("Ese nombre ya existe"), HttpStatus.BAD_REQUEST);
        if(userService.existsByEmail(newUser.getEmail()))
            return new ResponseEntity(new MessageDto("Ese email ya existe"), HttpStatus.BAD_REQUEST);

        Customer customer = new Customer(newUser.getName(), newUser.getUserName(), newUser.getEmail(), passwordEncoder.encode((newUser.getPassword())));

        Set<Rol> roles = new HashSet<>();
        roles.add(rolService.getByRolName(RolName.ROLE_USER).get());

        if(newUser.getRoles().contains("admin"))
            roles.add(rolService.getByRolName(RolName.ROLE_ADMIN).get());
        customer.setRoles(roles);
        userService.save(customer);
        return new ResponseEntity(new MessageDto("Usuario registrado"), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtDto> login(@Valid @RequestBody LoginUser loginUser, BindingResult bindingResult) {
        if(bindingResult.hasErrors())
            return new ResponseEntity(new MessageDto("Campos mal ingresados"), HttpStatus.BAD_REQUEST);

                Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUser.getUserName(), loginUser.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateToken(authentication);
        UserDetails userDetails = (UserDetails)authentication.getPrincipal();
        JwtDto jwtDto = new JwtDto(jwt, userDetails.getUsername(),userDetails.getAuthorities());
        return new ResponseEntity(jwtDto, HttpStatus.OK);
    }

    @PostMapping("/nuevoE")
    public ResponseEntity<?> nuevoE(@Valid @RequestBody NewEnterprise newEnterprise, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return new ResponseEntity(new MessageDto("Campos mal ingresados o email inválido"), HttpStatus.BAD_REQUEST);
        if(userService.existsByUserName(newEnterprise.getUserName()))
            return new ResponseEntity(new MessageDto("Ese nombre ya existe"), HttpStatus.BAD_REQUEST);
        if(userService.existsByEmail(newEnterprise.getEmail()))
            return new ResponseEntity(new MessageDto("Ese email ya existe"), HttpStatus.BAD_REQUEST);

        Customer customer = new Customer(newEnterprise.getName(), newEnterprise.getUserName(), newEnterprise.getEmail(), passwordEncoder.encode((newEnterprise.getPassword())));

        Set<Rol> roles = new HashSet<>();
        roles.add(rolService.getByRolName(RolName.ROLE_ADMIN).get());

        if(newEnterprise.getRoles().contains("admin"))
            roles.add(rolService.getByRolName(RolName.ROLE_ADMIN).get());
        customer.setRoles(roles);
        Customer customerCreado = userService.save(customer);
        Enterprise enterprise = new Enterprise(customerCreado, newEnterprise.getNameE(),  newEnterprise.getImage_url(),
                                    newEnterprise.getMail(), newEnterprise.getPhone(), newEnterprise.getCi(),
                                    newEnterprise.getCategoryE(), newEnterprise.getAddress());
        //userService.save();
        return new ResponseEntity(new MessageDto("Usuario registrado"), HttpStatus.CREATED);
    }

}
