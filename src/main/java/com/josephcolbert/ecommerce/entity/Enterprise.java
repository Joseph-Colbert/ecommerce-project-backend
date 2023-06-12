package com.josephcolbert.ecommerce.entity;

import lombok.Data;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name="enterprise")
@Data    // Genera los getters y setters por detras
public class Enterprise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "enterprise")    // por la coleccion de productos
    private Set<Product> products;

    @Column(name = "name")
    private String name;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "mail")
    private String mail;

    @Column(name = "phone")
    private String phone;

    @Column(name = "ci")
    private String ci;

    @ManyToOne
    @JoinColumn(name = "enterprise_category_id", nullable = false)
    private EnterpriseCategory categoryE;

    @Column(name = "address")
    private String address;

    public Enterprise() {

    }

    public Enterprise(Long id, Customer customer, Set<Product> products, String name, String imageUrl, String mail, String phone, String ci, EnterpriseCategory categoryE, String address) {
        this.id = id;
        this.customer = customer;
        this.products = products;
        this.name = name;
        this.imageUrl = imageUrl;
        this.mail = mail;
        this.phone = phone;
        this.ci = ci;
        this.categoryE = categoryE;
        this.address = address;
    }
}
