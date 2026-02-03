package com.example.click_n_shop_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.click_n_shop_api.model.WishListProduct;

public interface WishListProductRepository extends JpaRepository<WishListProduct, Long>{

}
