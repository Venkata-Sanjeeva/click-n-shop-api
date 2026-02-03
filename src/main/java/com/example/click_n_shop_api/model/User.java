package com.example.click_n_shop_api.model;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Data
@Table(name = "users") // This changes the table name to 'users', which is safe
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String uniqueId;
    private String fullName;
    private String username;
    private String password;
    private String email;
    private String phone;
    private String address;
    private String city;
    private String country;
    private String zipcode;
    private String state;
    private String gender;
    private Date dob;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "user")
    private Cart cart;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "user")
    private WishList wishList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Order> orders;
}