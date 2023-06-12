package com.josephcolbert.ecommerce.dto;

import com.josephcolbert.ecommerce.entity.Customer;
import com.josephcolbert.ecommerce.entity.OrderItemOnCredit;
import com.josephcolbert.ecommerce.entity.OrderOnCredit;
import lombok.Data;

import java.util.Set;

@Data
public class PurchaseOnCreditPayment {
    private Customer customer;
    private OrderOnCredit orderOnCredit;
    private Set<OrderItemOnCredit> orderItemsOnCredit;
}
