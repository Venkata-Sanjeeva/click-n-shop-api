package com.example.click_n_shop_api.exceptions;

public class WishListProductNotFoundException extends RuntimeException{
    public WishListProductNotFoundException(String message) {
        super(message);
    }
}
