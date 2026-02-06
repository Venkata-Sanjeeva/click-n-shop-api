package com.example.click_n_shop_api.exceptions;

public class CartItemAlreadyFoundException extends RuntimeException{
	public CartItemAlreadyFoundException(String message) {
		super(message);
	}
}
