package com.josephcolbert.ecommerce.emailpassword.controller;

import com.josephcolbert.ecommerce.emailpassword.dto.EmailValuesDto;
import com.josephcolbert.ecommerce.emailpassword.service.EmailService;
import com.josephcolbert.ecommerce.entity.Customer;
import com.josephcolbert.ecommerce.security.securityDto.MessageDto;
import com.josephcolbert.ecommerce.security.securityService.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/email-password")
@CrossOrigin
public class EmailController {
    @Autowired
    EmailService emailService;
    @Autowired
    UserService userService;

    @Value("${spring.mail.username}")
    private String mailFrom;
    private static final String subject = "Cambio de Contraseña";

    @PostMapping("/send-email")
    public ResponseEntity<?> sendEmailTemplate(@RequestBody EmailValuesDto dto) {
        Optional<Customer> customerOpt = userService.getByUserNameOrEmail(dto.getMailTo());
        if(!customerOpt.isPresent())
            return new ResponseEntity(new MessageDto("No existe ningun usuario con esas credenciales"), HttpStatus.NOT_FOUND);
        Customer customer = customerOpt.get();
        dto.setMailFrom(mailFrom);
        dto.setMailTo(customer.getEmail());
        dto.setSubject(subject);
        dto.setUserName(customer.getUserName());
        UUID uuid = UUID.randomUUID();
        String tokenPassword = uuid.toString();
        dto.setTokenPassword(tokenPassword);
        customer.setTokenPassword(tokenPassword);
        userService.save(customer);
        emailService.sendEmail(dto);
        return new ResponseEntity(new MessageDto("Te enviamos un correo"), HttpStatus.OK);

    }
}


