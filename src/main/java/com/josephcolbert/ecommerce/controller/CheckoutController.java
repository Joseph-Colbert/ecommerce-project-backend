package com.josephcolbert.ecommerce.controller;

import com.josephcolbert.ecommerce.dto.*;
import com.josephcolbert.ecommerce.entity.Product;
import com.josephcolbert.ecommerce.security.securityDto.MessageDto;
import com.josephcolbert.ecommerce.service.CheckoutService;
import com.josephcolbert.ecommerce.service.ProductService;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.logging.Logger;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/checkout")
public class CheckoutController {

    private Logger logger = Logger.getLogger(getClass().getName());
    private final CheckoutService checkoutService;

    public CheckoutController(CheckoutService checkoutService, CheckoutService checkoutOnCreditService) {
        this.checkoutService = checkoutService;
        this.checkoutOnCreditService = checkoutOnCreditService;
    }

    // para compra directa
    @PostMapping("/purchase")
    public PurchaseResponse placeOrder(@RequestBody Purchase purchase) {
        PurchaseResponse purchaseResponse = checkoutService.placeOrder(purchase);
        return purchaseResponse;
    }

    @PostMapping("/payment-intent")
    public ResponseEntity<String> createPaymentIntent(@RequestBody PaymentInfo paymentInfo) throws StripeException {

       logger.info("paymentInfo.amount: " + paymentInfo.getAmount());
        PaymentIntent paymentIntent = checkoutService.createPaymentIntent(paymentInfo);

        String paymentStr = paymentIntent.toJson();

        return new ResponseEntity<>(paymentStr, HttpStatus.OK);
    }



    private final CheckoutService checkoutOnCreditService;



    // para compra a credito
    @PostMapping("/purchaseoncredit")
    public PurchaseResponse placeOrderOnCredit(@RequestBody PurchaseOnCredit purchaseOnCredit) {
        PurchaseResponse purchaseResponse = checkoutService.placeOrderOnCredit(purchaseOnCredit);
        return purchaseResponse;
    }


    @PostMapping("/payment-intent-on-credit")
    public ResponseEntity<String> createPaymentIntentOnCredit(@RequestBody PaymentInfo paymentInfo) throws StripeException {

        logger.info("paymentInfo.amount: " + paymentInfo.getAmount());
        PaymentIntent paymentIntent = checkoutService.createPaymentIntentOnCredit(paymentInfo);

        String paymentStr = paymentIntent.toJson();

        return new ResponseEntity<>(paymentStr, HttpStatus.OK);
    }



    // siguiente pago para compra a credito
    @PutMapping("/purchaseoncreditpayment")
    public MessageDto placeOrderOnCreditPayment(@RequestBody PurchaseOnCreditPayment purchaseOnCreditPayment) {
        MessageDto messageDto = checkoutService.placeOrderOnCreditPayment(purchaseOnCreditPayment);
        return messageDto;
    }


    @PostMapping("/new-payment-intent-on-credit")
    public ResponseEntity<String> createPaymentIntentOnCreditA(@RequestBody PaymentInfo paymentInfo) throws StripeException {

        logger.info("paymentInfo.amount: " + paymentInfo.getAmount());
        PaymentIntent paymentIntent = checkoutService.createPaymentIntentOnCreditA(paymentInfo);

        String paymentStr = paymentIntent.toJson();

        return new ResponseEntity<>(paymentStr, HttpStatus.OK);
    }












    //para el crud
    @Autowired
    ProductService productService;


    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody ProductDto productDto) {
        if (StringUtils.isBlank(productDto.getName()))
            return new ResponseEntity(new MessageDto("El nombre es obligatorio"), HttpStatus.BAD_REQUEST);
        if (productDto.getUnitPrice() == null || productDto.getUnitPrice().compareTo(BigDecimal.ZERO) < 0)
            return new ResponseEntity(new MessageDto("El precio debe ser mayor a 0"), HttpStatus.BAD_REQUEST);
      /*  if (productService.existByName(productDto.getName()))
            return new ResponseEntity(new MessageDto("Ese nombre ya existe"), HttpStatus.BAD_REQUEST);*/
        Product product = new Product();
        product.setName(productDto.getName());
        product.setActive(productDto.isActive());
        product.setDateCreated(new Date());
        product.setDescription(productDto.getDescription());
        product.setSku(productDto.getSku());
        product.setImageUrl(productDto.getImageUrl());
        product.setLastUpdated(new Date());
        product.setUnitsInStock(productDto.getUnitsInStock());
        product.setUnitPrice(productDto.getUnitPrice());

        productService.save(product);
        return new ResponseEntity(new MessageDto("producto creado"), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id")int id, @RequestBody ProductDto productDto) {
        if (!productService.existById(id))
            return new ResponseEntity(new MessageDto("No existe"), HttpStatus.NOT_FOUND);
      /*  if (productService.existByName(productDto.getName()) && productService.getByName(productDto.getName()).get().getId() != id)
            return new ResponseEntity(new MessageDto("Ese nombre ya existe"), HttpStatus.BAD_REQUEST);*/
        if (StringUtils.isBlank(productDto.getName()))
            return new ResponseEntity(new MessageDto("El nombre es obligatorio"), HttpStatus.BAD_REQUEST);
        if (productDto.getUnitPrice() == null ||  productDto.getUnitPrice().compareTo(BigDecimal.ZERO) < 0)
            return new ResponseEntity(new MessageDto("El precio debe ser mayor a 0"), HttpStatus.BAD_REQUEST);

        Product product = productService.getOne(id).get();
        product.setName(productDto.getName());
        product.setUnitPrice(productDto.getUnitPrice());
        productService.save(product);
        return new ResponseEntity(new MessageDto("producto actualizado"), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id")int id) {
        if (!productService.existById(id))
            return new ResponseEntity(new MessageDto("No existe"), HttpStatus.NOT_FOUND);
        productService.delete(id);
        return new ResponseEntity(new MessageDto("Producto eliminado"), HttpStatus.OK);
        }
}
