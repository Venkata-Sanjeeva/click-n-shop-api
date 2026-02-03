package com.example.click_n_shop_api.repository;

import com.example.click_n_shop_api.model.Cart;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
    // You can add custom methods here if needed
	Optional<Cart> findByUserId(Long userId);
}
