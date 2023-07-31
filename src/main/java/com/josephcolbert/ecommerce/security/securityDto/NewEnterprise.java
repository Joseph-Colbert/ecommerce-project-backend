package com.josephcolbert.ecommerce.security.securityDto;

import com.josephcolbert.ecommerce.entity.EnterpriseCategory;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.HashSet;
import java.util.Set;

public class NewEnterprise {
    @NotBlank
    private String name;
    @NotBlank
    private String userName;
    @Email
    private String email;
    @NotBlank
    private String password;

    private String address;
    private String image_url;
    private String ci;
    private String mail;
    private String nameE;
    private String  phone;
    private EnterpriseCategory categoryE;

    public EnterpriseCategory getCategoryE() {
        return categoryE;
    }

    public void setCategoryE(EnterpriseCategory categoryE) {
        this.categoryE = categoryE;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getCi() {
        return ci;
    }

    public void setCi(String ci) {
        this.ci = ci;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getNameE() {
        return nameE;
    }

    public void setNameE(String nameE) {
        this.nameE = nameE;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEnterprise_category_id() {
        return enterprise_category_id;
    }

    public void setEnterprise_category_id(String enterprise_category_id) {
        this.enterprise_category_id = enterprise_category_id;
    }

    private String enterprise_category_id;


    private Set<String> roles = new HashSet<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
}
