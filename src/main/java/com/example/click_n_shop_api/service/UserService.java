package com.example.click_n_shop_api.service;

import com.example.click_n_shop_api.exceptions.UserExistsException;
import com.example.click_n_shop_api.exceptions.UserNotFoundException;
import com.example.click_n_shop_api.model.Cart;
import com.example.click_n_shop_api.model.Order;
import com.example.click_n_shop_api.model.User;
import com.example.click_n_shop_api.model.WishList;
import com.example.click_n_shop_api.repository.UserRepository;
import com.example.click_n_shop_api.request.RegisterRequest;
import com.example.click_n_shop_api.request.ResetPasswordRequest;
import com.example.click_n_shop_api.request.UpdateProfileDetailsRequest;
import com.example.click_n_shop_api.response.GetAllUsersResponse;
import com.example.click_n_shop_api.response.UpdatedProfileResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;

    private UpdatedProfileResponse getUpdatedProfileDetails(UpdateProfileDetailsRequest userProfileDetails, String userUniqueId) {
        String email = userProfileDetails.getEmail();
        String fullName = userProfileDetails.getFullName();
        String phone = userProfileDetails.getPhone();
        String address = userProfileDetails.getAddress();
        String country = userProfileDetails.getCountry();
        String state = userProfileDetails.getState();
        String city = userProfileDetails.getCity();
        String zipcode = userProfileDetails.getZipcode();
        String gender = userProfileDetails.getGender();
        Date dob = userProfileDetails.getDob();

        UpdatedProfileResponse user = new UpdatedProfileResponse();
        user.setUniqueId(userUniqueId);

        if(email == null || email.isEmpty()) {
            email = "";
        }
        user.setEmail(email);

        if(fullName == null || fullName.isEmpty()) {
            fullName = "";
        }
        user.setFullName(fullName);

        if(phone == null || phone.isEmpty()) {
            phone = "";
        }
        user.setPhone(phone);

        if(address == null || address.isEmpty()) {
            address = "";
        }
        user.setAddress(address);

        if(country == null || country.isEmpty()) {
            country = "";
        }
        user.setCountry(country);

        if(state == null || state.isEmpty()) {
            state = "";
        }
        user.setState(state);

        if(city == null || city.isEmpty()) {
            city = "";
        }
        user.setCity(city);

        if(zipcode == null || zipcode.isEmpty()) {
            zipcode = "";
        }
        user.setZipcode(zipcode);

        if(gender == null || gender.isEmpty()) {
            gender = "";
        }
        user.setGender(gender);

        if(dob != null) {
            user.setDob(dob);
        }

        return user;
    }

    private String generateUserId() {
        return "USER-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    @Transactional
    public User saveNewUser(RegisterRequest registerReq) {

        if(
                (userRepo.findByUsername(registerReq.getUsername())).isPresent() ||
                (userRepo.existsByEmail(registerReq.getEmail()))
        ) {
            throw new UserExistsException("User with Email: " + registerReq.getEmail() + " already exists!!!");
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
    	User user = userRepo.findByUniqueId(uniqueId).orElse(null);
    	
    	if(user == null) {
    		throw new UserNotFoundException("User not found with ID: " + uniqueId);
    	}
        return user;
    }

    public UpdatedProfileResponse fetchUserProfileDetailsByUniqueId(String userUniqueId) {
        User user = userRepo.findByUniqueId(userUniqueId).orElse(null);
        if(user == null) {
            throw new UserNotFoundException("User with ID: " + userUniqueId + " not found!");
        }
        return getUpdatedProfileDetails(new UpdateProfileDetailsRequest(user), userUniqueId);
    }

    public UpdatedProfileResponse updateUserProfile(UpdateProfileDetailsRequest userProfileDetails, String userUniqueId) {
    	
    	User user = userRepo.findByUniqueId(userUniqueId).orElse(null);
    	
    	if(user == null) {
    		throw new UserNotFoundException("User not found with ID: " + userUniqueId);
    	}

        UpdatedProfileResponse updatedResObj = getUpdatedProfileDetails(userProfileDetails, userUniqueId);

        user.setEmail(updatedResObj.getEmail());
        user.setFullName(updatedResObj.getFullName());
        user.setPhone(updatedResObj.getPhone());
        user.setGender(updatedResObj.getGender());
        user.setDob(updatedResObj.getDob());
        user.setAddress(updatedResObj.getAddress());
        user.setCountry(updatedResObj.getCountry());
        user.setState(updatedResObj.getState());
        user.setCity(updatedResObj.getCity());
        user.setZipcode(updatedResObj.getZipcode());

    	userRepo.save(user);
    	
    	return updatedResObj;
    }

    public User resetPassword(ResetPasswordRequest resetPasswordReq) {
        User user = userRepo.findByUniqueId(resetPasswordReq.getUserUniqueId()).orElse(null);

        if(user == null) {
            throw new UserNotFoundException("User not found with ID: " + resetPasswordReq.getUserUniqueId());
        }

        user.setPassword(resetPasswordReq.getPassword());

        return userRepo.save(user);
    }

    public GetAllUsersResponse fetchAllUsers() {
        return new GetAllUsersResponse(userRepo.findAll());
    }
}
