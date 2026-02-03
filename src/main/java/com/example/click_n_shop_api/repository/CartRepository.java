package com.example.click_n_shop_api.repository;

import com.example.click_n_shop_api.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface CartRepository extends JpaRepository<Cart, Long> {
    // You can add custom methods here if needed
}
