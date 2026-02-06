package com.example.click_n_shop_api.controller;

import com.example.click_n_shop_api.exceptions.UserExistsException;
import com.example.click_n_shop_api.exceptions.UserNotFoundException;
import com.example.click_n_shop_api.model.User;
import com.example.click_n_shop_api.request.RegisterRequest;
import com.example.click_n_shop_api.request.ResetPasswordRequest;
import com.example.click_n_shop_api.request.UpdateProfileDetailsRequest;
import com.example.click_n_shop_api.response.UpdatedProfileResponse;
import com.example.click_n_shop_api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/user")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerNewUser(@RequestBody RegisterRequest registerReq) {
        try {
            User savedUser = userService.saveNewUser(registerReq);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
        } catch (UserExistsException existsException) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User with this email already exists.");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error registering user..." + "\n" + e.getMessage());
        }
    }

    @GetMapping("/fetch/{userUniqueId}")
    public ResponseEntity<?> getUserByUniqueId(@PathVariable String userUniqueId) {
    	try {
        	User user = userService.fetchUserByUniqueId(userUniqueId);
            return ResponseEntity.ok(user);
		} catch (UserNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with ID: " + userUniqueId + " not found!");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error getting user with the ID: " + userUniqueId + "\n" + e.getMessage());
		}
    }

    @GetMapping("/fetch/profile-details/{userUniqueId}")
    public ResponseEntity<?> getUserProfileDetails(@PathVariable String userUniqueId) {
        try {
            UpdatedProfileResponse updatedProRes = userService.fetchUserProfileDetailsByUniqueId(userUniqueId);
            return ResponseEntity.ok(updatedProRes);
        } catch(UserNotFoundException userNotFoundException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with ID: " + userUniqueId + " not found!");
        } catch (Exception ex) {
            return ResponseEntity.internalServerError().body("Error fetching profile details of user with ID: " + userUniqueId);
        }
    }
    
    @PatchMapping("/update/profile-details/{userUniqueId}")
    public ResponseEntity<?> updateUserProfileDetails(
    		@RequestBody UpdateProfileDetailsRequest userProfileDetails,
    		@PathVariable String userUniqueId) {
        try {
            UpdatedProfileResponse profileResponse = userService.updateUserProfile(userProfileDetails, userUniqueId);
            return ResponseEntity.ok(profileResponse);
        } catch(UserNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User with ID: " + userUniqueId + " not found!");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating user profile details..." + "\n" + ex.getMessage());
        }
    }

    @PatchMapping("/update/resetPassword")
    public ResponseEntity<?> updateUserPassword(@RequestBody ResetPasswordRequest resetPasswordReq) {
        try {
            User user = userService.resetPassword(resetPasswordReq);
            return ResponseEntity.ok(user);
        } catch (UserNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with ID: " + resetPasswordReq.getUserUniqueId() + " not found!");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error resetting password for user with ID: " + resetPasswordReq.getUserUniqueId() + "\n" + e.getMessage());
        }

    }

    @GetMapping("/fetch/all")
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok(userService.fetchAllUsers());
    }
}
