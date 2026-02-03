package com.example.click_n_shop_api.service;

import com.example.click_n_shop_api.model.Cart;
import com.example.click_n_shop_api.model.Order;
import com.example.click_n_shop_api.model.User;
import com.example.click_n_shop_api.model.WishList;
import com.example.click_n_shop_api.repository.UserRepository;
import com.example.click_n_shop_api.request.RegisterRequest;
import com.example.click_n_shop_api.request.ResetPasswordRequest;
import com.example.click_n_shop_api.response.GetAllUsersResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;

    private String generateUserId() {
        return "USER-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    @Transactional
    public User saveNewUser(RegisterRequest registerReq) {

        if(
                (userRepo.findByUsername(registerReq.getUsername())).isPresent() ||
                (userRepo.existsByEmail(registerReq.getEmail()))
        ) {
            return null;
        }

        User newUser = new User();

        newUser.setUniqueId(generateUserId());
        newUser.setEmail(registerReq.getEmail());
        newUser.setPassword(registerReq.getPassword());
        newUser.setUsername(registerReq.getUsername());
        
     // Initialize Cart and Wishlist for new user
        Cart cart = new Cart();
        cart.setUser(newUser);
        newUser.setCart(cart);

        WishList wishList = new WishList();
        wishList.setUser(newUser);
        newUser.setWishList(wishList);
        
        List<Order> listOfOrders = new ArrayList<Order>();
        newUser.setOrders(listOfOrders);

        return userRepo.save(newUser);
    }

    public User fetchUserByUniqueId(String uniqueId) {
        return userRepo.findByUniqueId(uniqueId).orElse(null);
    }

    public User resetPassword(ResetPasswordRequest resetPasswordReq) {
        User user = userRepo.findByUniqueId(resetPasswordReq.getUserUniqueId()).orElse(null);

        if(user == null) {
            return null;
        }

        user.setPassword(resetPasswordReq.getPassword());

        return userRepo.save(user);
    }

    public GetAllUsersResponse fetchAllUsers() {
        return new GetAllUsersResponse(userRepo.findAll());
    }
}
