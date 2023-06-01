package com.josephcolbert.ecommerce.service;

import com.josephcolbert.ecommerce.dao.CustomerRepository;
import com.josephcolbert.ecommerce.dto.PaymentInfo;
import com.josephcolbert.ecommerce.dto.Purchase;
import com.josephcolbert.ecommerce.dto.PurchaseOnCredit;
import com.josephcolbert.ecommerce.dto.PurchaseResponse;
import com.josephcolbert.ecommerce.entity.*;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CheckoutServiceImpl implements CheckoutService{

    private CustomerRepository customerRepository;

    public CheckoutServiceImpl(CustomerRepository customerRepository,
                               @Value("${stripe.key.secret}") String secretKey) {
        this.customerRepository = customerRepository;

        // Initialize Stripe API with secret key
        Stripe.apiKey = secretKey;
    }

    @Override
    @Transactional
    public PurchaseResponse placeOrder(Purchase purchase) {

        //retrieve the order info from dto
        Order order = purchase.getOrder();

        //generate tracking number
        String orderTrackingNumber = generateOrderTrackingNumber();
        order.setOrderTrackingNumber((orderTrackingNumber));

        //populate order with orderItems
        Set<OrderItem> orderItems = purchase.getOrderItems();
        orderItems.forEach(item -> order.add(item));

        //populate order with billingAddress and shippingAddress
        //order.setBillingAddress(purchase.getBillingAddress());
        order.setShippingAddress(purchase.getShippingAddress());

        order.setCustomer(purchase.getCustomer());
        //populate customer with order
        Customer customer = purchase.getCustomer();

        //verificar si es un cliente existente
        String theEmail = customer.getEmail();

        Customer customerFromDB = customerRepository.findByEmail(theEmail);

        if (customerFromDB != null) {
            customer = customerFromDB;
        }

        customer.add(order);


        //save to the database
        //customerRepository.save(customer);

        //return a response
        return new PurchaseResponse(orderTrackingNumber);
    }

    @Override
    public PaymentIntent createPaymentIntent(PaymentInfo paymentInfo) throws StripeException {

        List<String> paymentMethodTypes = new ArrayList<>();
        paymentMethodTypes.add("card");

        Map<String, Object> params = new HashMap<>();
        params.put("amount", paymentInfo.getAmount());
        params.put("currency", paymentInfo.getCurrency());
        params.put("payment_method_types", paymentMethodTypes);
        return PaymentIntent.create(params);
    }

    @Override
    @Transactional
    public PurchaseResponse placeOrderOnCredit(PurchaseOnCredit purchaseOnCredit) {
        //retrieve the order info from dto
        OrderOnCredit orderOnCredit = purchaseOnCredit.getOrderOnCredit();

        //generate tracking number
        String orderTrackingNumber = generateOrderTrackingNumber();
        orderOnCredit.setOrderTrackingNumber((orderTrackingNumber));

        //populate order with orderItems
        Set<OrderItemOnCredit> orderItemsOnCredit = purchaseOnCredit.getOrderItemsOnCredit();
        orderItemsOnCredit.forEach(item -> orderOnCredit.add(item));

        //populate order with shippingAddress
        orderOnCredit.setShippingAddress(purchaseOnCredit.getShippingAddress());

        //populate customer with order
        Customer customer = purchaseOnCredit.getCustomer();

        //verificar si es un cliente existente
        String theEmail = customer.getEmail();

        Customer customerFromDB = customerRepository.findByEmail(theEmail);

        if (customerFromDB != null) {
            customer = customerFromDB;
        }

        customer.add(orderOnCredit);

        //save to the database
        //customerRepository.save(customer);


        return new PurchaseResponse(orderTrackingNumber);

    }

    @Override
    public PaymentIntent createPaymentIntentOnCredit(PaymentInfo paymentInfo) throws StripeException {

        List<String> paymentMethodTypes = new ArrayList<>();
        paymentMethodTypes.add("card");

        Map<String, Object> params = new HashMap<>();
        params.put("amount", paymentInfo.getAmount());
        params.put("currency", paymentInfo.getCurrency());
        params.put("payment_method_types", paymentMethodTypes);
        return PaymentIntent.create(params);
    }

    private String generateOrderTrackingNumber() {
        // generate a random UUID number (UUID version-4) (Universal Unique IDentifier)
        return UUID.randomUUID().toString();
    }
}
