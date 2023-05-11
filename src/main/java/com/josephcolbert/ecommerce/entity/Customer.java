package com.josephcolbert.ecommerce.entity;


import com.josephcolbert.ecommerce.security.securityEntity.Rol;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="customer")
@Getter
@Setter
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    private String name;

    @Column(unique = true)
    private String userName;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Order> orders = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "rol_user", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "rol_id"))
    private Set<Rol> roles = new HashSet<>();


    public void add (Order order) {

        if (order !=null) {
            if (orders == null) {
                orders = new HashSet<>();
            }
            orders.add(order);
            order.setCustomer(this);
        }
    }

    public Customer(String name, String userName, String email, String password) {
        this.name = name;
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

    public Customer() {
    }

    public Customer(Long id, String firstName, String lastName, String name, String userName, String email, String password, Set<Order> orders, Set<Rol> roles) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.name = name;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.orders = orders;
        this.roles = roles;
    }
}
