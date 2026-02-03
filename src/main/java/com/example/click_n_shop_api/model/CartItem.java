package com.example.click_n_shop_api.model;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class CartItem {
    private String productId;
    private Integer quantity;
}
