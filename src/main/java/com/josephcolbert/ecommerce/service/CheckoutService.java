package com.josephcolbert.ecommerce.service;

import com.josephcolbert.ecommerce.dto.*;
import com.josephcolbert.ecommerce.entity.OrderItemOnCredit;
import com.josephcolbert.ecommerce.security.securityDto.MessageDto;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;

public interface CheckoutService {

    // para compra directa
    PurchaseResponse placeOrder(Purchase purchase);

    PaymentIntent createPaymentIntent(PaymentInfo paymentInfo) throws StripeException;


    // para compra a credito
    PurchaseResponse placeOrderOnCredit(PurchaseOnCredit purchaseOnCredit);

    PaymentIntent createPaymentIntentOnCredit(PaymentInfo paymentInfo) throws StripeException;

    // para los siguientes pagos a credito
    MessageDto placeOrderOnCreditPayment(OrderItemOnCredit orderItemOnCredit);

    PaymentIntent createPaymentIntentOnCreditA(PaymentInfo paymentInfo) throws StripeException;


}
