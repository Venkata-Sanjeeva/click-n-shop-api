package com.example.click_n_shop_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.click_n_shop_api.model.WishList;
import com.example.click_n_shop_api.request.AddWishListRequest;
import com.example.click_n_shop_api.service.WishListService;

@RestController
@RequestMapping("/api/wishList")
@CrossOrigin
public class WishListController {

	@Autowired
	private WishListService wishListService;
	
	@PostMapping("/save")
	public ResponseEntity<?> saveWishListProduct(@RequestBody AddWishListRequest addWishListReq) {
		WishList wishList = wishListService.saveWishListProduct(addWishListReq);
		if(wishList == null) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error Saving WishList");
		}
		
		return ResponseEntity.ok(wishList);
	}
}
