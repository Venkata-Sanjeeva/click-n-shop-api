package com.example.click_n_shop_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.click_n_shop_api.model.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
	
}
