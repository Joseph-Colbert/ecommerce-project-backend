package com.josephcolbert.ecommerce.dto;

import com.josephcolbert.ecommerce.entity.Address;
import com.josephcolbert.ecommerce.entity.Customer;
import com.josephcolbert.ecommerce.entity.Order;
import com.josephcolbert.ecommerce.entity.OrderItem;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class Purchase {

    private Customer customer;
    private Address shippingAddress;
    //private Address billingAddress;
    private Order order;
    private List<OrderItem> orderItems;
}
