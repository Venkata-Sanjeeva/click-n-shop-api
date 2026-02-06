package com.example.click_n_shop_api.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Data
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    @ToString.Exclude             // Breaks the toString loop
    @EqualsAndHashCode.Exclude    // Breaks the hashCode loop
    private User user;

    @OneToMany(mappedBy = "cart")
    private List<CartItem> cartItems;
    
}