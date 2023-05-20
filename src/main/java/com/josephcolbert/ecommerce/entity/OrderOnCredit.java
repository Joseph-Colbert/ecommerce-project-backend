package com.josephcolbert.ecommerce.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name="ordersOnCredits")
@Getter
@Setter
public class OrderOnCredit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="order_tracking_number")
    private String orderTrackingNumber;

    @Column(name="total_quantity")
    private int totalQuantity;

    @Column(name="total_price_on_credit")
    private BigDecimal totalPriceOnCredit;

    @Column(name="payment")
    private BigDecimal payment;

    @Column(name="status")
    private String status;

    @Column(name="delay")
    private int delay;

    @Column(name="monthly_fees_to_pay")  //  cuotas que faltan pagar
    private int monthlyFeesToPay;

    @Column(name="monthly_fees_paid")  //  cuotas pagadas
    private int monthlyFeesPaid;

    @Column(name="date_created")
    @CreationTimestamp
    private Date dateCreated;

    @Column(name="last_updated")
    @UpdateTimestamp
    private Date lastUpdated;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "orderOnCredit")
    private Set<OrderItemOnCredit> orderItemsOnCredit = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "shipping_address_id", referencedColumnName = "id")
    private Address shippingAddress;

    public void add(OrderItemOnCredit item) {
        if (item != null) {
            if (orderItemsOnCredit == null) {
                orderItemsOnCredit = new HashSet<>();
            }
            orderItemsOnCredit.add(item);
            item.setOrderOnCredit(this);
        }
    }
}
