package com.josephcolbert.ecommerce.service;

import com.josephcolbert.ecommerce.dto.PaymentInfo;
import com.josephcolbert.ecommerce.dto.Purchase;
import com.josephcolbert.ecommerce.dto.PurchaseOnCredit;
import com.josephcolbert.ecommerce.dto.PurchaseResponse;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;

public interface CheckoutService {

    // para compra directa
    PurchaseResponse placeOrder(Purchase purchase);

    PaymentIntent createPaymentIntent(PaymentInfo paymentInfo) throws StripeException;


    // para compra a credito
    PurchaseResponse placeOrderOnCredit(PurchaseOnCredit purchaseOnCredit);

}
