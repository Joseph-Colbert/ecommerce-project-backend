package com.josephcolbert.ecommerce.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


@Entity
@Table(name="order_item_on_credit")
@Getter
@Setter
public class OrderItemOnCredit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="image_url")
    private String imageUrl;

    @Column(name="unit_price")
    private BigDecimal unitPrice;

    @Column(name="unit_price_on_credit")
    private BigDecimal unitPriceOnCredit;

    @Column(name="monthly_fees")  // monto mensual a pagar
    private BigDecimal monthly_fees;

    @Column(name="number_of_fees")  // numero de cuotas pagadas
    private int number_of_fees;

    @Column(name="quantity")
    private int quantity;

    @Column(name="product_id")
    private Long productId;

    @ManyToOne
    @JoinColumn(name = "orderOnCredit_id")
    private OrderOnCredit orderOnCredit;
}
