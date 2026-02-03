package com.example.click_n_shop_api.model;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "orders")
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    private Date orderDate;
    private Date dueDate;
    private Double totalPrice;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
    private List<ProductOrderDetails> productsList;
}