package com.example.click_n_shop_api.repository;

import com.example.click_n_shop_api.model.WishList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface WishListRepository extends JpaRepository<WishList, Long> {
    WishList findByUserId(Long id);
}
