package com.josephcolbert.ecommerce.service;

import com.josephcolbert.ecommerce.dao.CustomerRepository;
import com.josephcolbert.ecommerce.dao.OrderItemOnCreditRepository;
import com.josephcolbert.ecommerce.dto.*;
import com.josephcolbert.ecommerce.entity.*;
import com.josephcolbert.ecommerce.security.securityDto.MessageDto;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import java.util.*;

@Service
@Slf4j
public class CheckoutServiceImpl implements CheckoutService{

    private CustomerRepository customerRepository;
    private final OrderItemOnCreditRepository orderItemOnCreditRepository;

    public CheckoutServiceImpl(CustomerRepository customerRepository,
                               @Value("${stripe.key.secret}") String secretKey, OrderItemOnCreditRepository orderItemOnCreditRepository) {
        this.customerRepository = customerRepository;
        this.orderItemOnCreditRepository = orderItemOnCreditRepository;

        // Initialize Stripe API with secret key
        Stripe.apiKey = secretKey;
    }

    //Pagos directos
    @Override
    @Transactional
    public PurchaseResponse placeOrder(Purchase purchase) {

        //retrieve the order info from dto
        Order order = purchase.getOrder();

        //generate tracking number
        String orderTrackingNumber = generateOrderTrackingNumber();
        order.setOrderTrackingNumber((orderTrackingNumber));

        //populate order with orderItems
        List<OrderItem> orderItems = purchase.getOrderItems();
        orderItems.forEach(item -> order.add(item));

        //populate order with billingAddress and shippingAddress
        //order.setBillingAddress(purchase.getBillingAddress());
        order.setShippingAddress(purchase.getShippingAddress());

        order.setCustomer(purchase.getCustomer());
        //populate customer with order
        Customer customer = purchase.getCustomer();

        //verificar si es un cliente existente
        //String theEmail = customer.getEmail();

        Customer customerFromDB = customerRepository.findByUserName(customer.getUserName());

        if (customerFromDB != null) {
            customer = customerFromDB;
        }
      //  log.info(customer.toString());

        customer.add(order);


        //save to the database
        customerRepository.save(customer);

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
        params.put("description","Compra a directa");
        params.put("receipt_email", paymentInfo.getReceiptEmail());
        return PaymentIntent.create(params);
    }

    //pagos a credito
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
       // String theEmail = customer.getEmail();

        Customer customerFromDB = customerRepository.findByUserName(customer.getUserName());

        if (customerFromDB != null) {
            customer = customerFromDB;
        }

        customer.add(orderOnCredit);

        //save to the database
        customerRepository.save(customer);


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
        params.put("description","Compra a crédito");
        params.put("receipt_email", paymentInfo.getReceiptEmail());
        return PaymentIntent.create(params);
    }


    //reiteracion de pagos a credito
    @Override
    @Transactional
    public MessageDto placeOrderOnCreditPayment(OrderItemOnCredit orderItemOnCredit) {
      OrderItemOnCredit orderItemOnCredit1 = orderItemOnCreditRepository.findById(orderItemOnCredit.getId()).get();
      orderItemOnCredit1.setNumberOfFees(orderItemOnCredit1.getNumberOfFees() + 1);
              orderItemOnCreditRepository.save(orderItemOnCredit1);


        return new MessageDto("Pago exitoso");

    }

    @Override
    public PaymentIntent createPaymentIntentOnCreditA(PaymentInfo paymentInfo) throws StripeException {

        List<String> paymentMethodTypes = new ArrayList<>();
        paymentMethodTypes.add("card");

        Map<String, Object> params = new HashMap<>();
        params.put("amount", paymentInfo.getAmount());
        params.put("currency", paymentInfo.getCurrency());
        params.put("payment_method_types", paymentMethodTypes);
        params.put("description","Siguiente pago de compra a crédito");
        params.put("receipt_email", paymentInfo.getReceiptEmail());
        return PaymentIntent.create(params);
    }


    private String generateOrderTrackingNumber() {
        // generate a random UUID number (UUID version-4) (Universal Unique IDentifier)
        return UUID.randomUUID().toString();
    }
}
