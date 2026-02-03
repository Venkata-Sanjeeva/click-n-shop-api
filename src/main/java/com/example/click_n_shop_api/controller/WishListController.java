package com.example.click_n_shop_api.controller;

import com.example.click_n_shop_api.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

	@GetMapping("/fetch/user/{userUniqueId}")
	public ResponseEntity<?> getWishListByUserId(@PathVariable String userUniqueId) {
		WishList wishList = null;
		try {
			wishList = wishListService.fetchWishListByUserId(userUniqueId);
		} catch (UserNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with ID: " + userUniqueId + " not found!");
		}
		return ResponseEntity.ok(wishList);
	}

	@DeleteMapping("/delete/{userUniqueId}/{productId}")
	public ResponseEntity<?> deleteWishListProduct(@PathVariable String userUniqueId,
												   @PathVariable String productId) {
		try {
			WishList updatedWishList = wishListService.deleteWishListProduct(userUniqueId, productId);
			return ResponseEntity.ok(updatedWishList);
		} catch (UserNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

}
