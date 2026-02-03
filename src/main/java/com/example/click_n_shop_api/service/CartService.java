package com.example.click_n_shop_api.service;

import com.example.click_n_shop_api.model.Cart;
import com.example.click_n_shop_api.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepo;

    public Cart saveCart() {

    }

}
