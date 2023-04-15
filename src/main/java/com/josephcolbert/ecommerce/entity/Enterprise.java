package com.josephcolbert.ecommerce.entity;

import lombok.Data;

import jakarta.persistence.*;

@Entity
@Table(name="enterprise")
@Data    // Genera los getters y setters por detras
public class Enterprise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_enterprise_enterprise_id", nullable = false)
    private UserEnterprise user;

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

}
