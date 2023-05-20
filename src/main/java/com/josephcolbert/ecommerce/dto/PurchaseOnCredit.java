package com.josephcolbert.ecommerce.dto;

import com.josephcolbert.ecommerce.entity.*;
import lombok.Data;

import java.util.Set;

@Data
public class PurchaseOnCredit {
    private Customer customer;
    private Address shippingAddress;
    private OrderOnCredit orderOnCredit;
    private Set<OrderItemOnCredit> orderItemsOnCredit;
}
