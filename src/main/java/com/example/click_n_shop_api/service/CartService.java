package com.example.click_n_shop_api.service;

import com.example.click_n_shop_api.exceptions.CartItemAlreadyFoundException;
import com.example.click_n_shop_api.exceptions.UserNotFoundException;
import com.example.click_n_shop_api.model.Cart;
import com.example.click_n_shop_api.model.CartItem;
import com.example.click_n_shop_api.model.User;
import com.example.click_n_shop_api.repository.CartItemRepository;
import com.example.click_n_shop_api.repository.CartRepository;
import com.example.click_n_shop_api.request.AddCartRequest;

import java.util.ArrayList;
import java.util.List;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepo;
    
    @Autowired
    private CartItemRepository cartItemRepo;
    
    @Autowired
    private UserService userService;
    
    public Cart getCartByUserId(Long userId) {
        // Assuming your CartRepository has a custom finder or you fetch via User
        return cartRepo.findAll().stream()
                .filter(c -> c.getUser().getId().equals(userId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Cart not found"));
    }

    public Cart saveCart(AddCartRequest addCartReq) {
    	User user = userService.fetchUserByUniqueId(addCartReq.getUserUniqueId());
    	
    	if(user == null) {
			throw new UserNotFoundException("User not found with ID: " + addCartReq.getUserUniqueId());
		}
		
		Cart userCart = user.getCart();
		
		CartItem cartItem = addCartReq.getCartItem();
		
		if(cartItemRepo.findByProductIdAndCartId(cartItem.getProductId(), userCart.getId()).orElse(null) != null) {
			throw new CartItemAlreadyFoundException("Cart Item Already Found!");
		}
		
		
		cartItem.setCart(userCart);
		
		cartItemRepo.save(cartItem);
		
		return userCart;
    }

    public Cart fetchCartByUserUniqueId(String userUniqueId) throws UserNotFoundException {
    	User user = userService.fetchUserByUniqueId(userUniqueId);
    	
    	if(user == null) {
    		throw new UserNotFoundException("User not found with ID: " + userUniqueId);
    	}
    	
    	return cartRepo.findByUserId(user.getId()).orElse(null);
    }
    
    public Cart deleteCartItemByProductId(String userUniqueId, String productId) throws UserNotFoundException {
    	User user = userService.fetchUserByUniqueId(userUniqueId);
    	
    	if(user == null) {
    		throw new UserNotFoundException("User not found with ID: " + userUniqueId);
    	}
    	
    	Cart cart = user.getCart();
    	CartItem cartItem = cartItemRepo.findByProductIdAndCartId(productId, cart.getId()).orElse(null);
    	
    	cartItemRepo.delete(cartItem);
    	
    	return cart;
    }
}
