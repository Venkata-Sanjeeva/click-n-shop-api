package com.example.click_n_shop_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.click_n_shop_api.model.WishListProduct;

import java.util.Optional;

public interface WishListProductRepository extends JpaRepository<WishListProduct, Long>{
    Optional<WishListProduct> findByProductIdAndWishListId(String productId, Long wishListId);
}
