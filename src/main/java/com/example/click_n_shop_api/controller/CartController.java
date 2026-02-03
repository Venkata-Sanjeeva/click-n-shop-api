package com.example.click_n_shop_api.controller;

import com.example.click_n_shop_api.exceptions.UserNotFoundException;
import com.example.click_n_shop_api.model.Cart;
import com.example.click_n_shop_api.request.AddCartRequest;
import com.example.click_n_shop_api.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
    	Cart savedCart = cartService.saveCart(cartReq);
    	if(savedCart == null) {
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error Saving Cart...");
    	}
    	return ResponseEntity.status(HttpStatus.OK).body(savedCart);
    }
    
    @GetMapping("/user/{uniqueId}")
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
}
