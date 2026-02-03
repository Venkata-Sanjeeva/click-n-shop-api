package com.example.click_n_shop_api.request;

import com.example.click_n_shop_api.model.CartItem;
import lombok.Data;

import java.util.List;

@Data
public class AddCartRequest {
    private String userUniqueId;
    private CartItem cartItem;
}
