package com.example.click_n_shop_api.controller;

import com.example.click_n_shop_api.exceptions.CartItemAlreadyFoundException;
import com.example.click_n_shop_api.exceptions.UserNotFoundException;
import com.example.click_n_shop_api.model.Cart;
import com.example.click_n_shop_api.model.WishList;
import com.example.click_n_shop_api.request.AddCartRequest;
import com.example.click_n_shop_api.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/save")
    public ResponseEntity<?> saveCartItem(@RequestBody AddCartRequest cartReq) {
    	try {
    		Cart savedCart = cartService.saveCart(cartReq);
        	
        	return ResponseEntity.status(HttpStatus.OK).body(savedCart);
		} catch (CartItemAlreadyFoundException e) {
			// TODO: handle exception
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error Saving Cart..." + e.getMessage());
		}
    }
    
    @GetMapping("/fetch/user/{uniqueId}")
    public ResponseEntity<?> getCartByUserUniqueId(@PathVariable String uniqueId) {
    	Cart cart = null;
    	try {
			cart = cartService.fetchCartByUserUniqueId(uniqueId);
		} catch (UserNotFoundException e) {
			// TODO: handle exception
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with ID: " + uniqueId + " not found!");
		}
    	
    	return ResponseEntity.ok(cart);
    }
    
    @DeleteMapping("/delete/{userUniqueId}/{productId}")
	public ResponseEntity<?> deleteWishListProduct(@PathVariable String userUniqueId,
												   @PathVariable String productId) {
		try {
			Cart updatedCart = cartService.deleteCartItemByProductId(userUniqueId, productId);
			return ResponseEntity.ok(updatedCart);
		} catch (UserNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}
}
