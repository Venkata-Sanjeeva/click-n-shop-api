package com.example.click_n_shop_api.request;

import com.example.click_n_shop_api.model.WishListProduct;

import lombok.Data;

@Data
public class AddWishListRequest {

	private String userUniqueId;
	private WishListProduct wishListProduct;
}
