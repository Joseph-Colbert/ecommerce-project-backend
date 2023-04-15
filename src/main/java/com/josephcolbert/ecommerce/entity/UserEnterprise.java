package com.josephcolbert.ecommerce.entity;

import lombok.Data;

import jakarta.persistence.*;

@Entity
@Table(name="user_enterprise")
@Data    // Genera los getters y setters por detras
public class UserEnterprise {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "password")
    private String password;

    @Column(name = "usertype")
    private String usertype;
}
