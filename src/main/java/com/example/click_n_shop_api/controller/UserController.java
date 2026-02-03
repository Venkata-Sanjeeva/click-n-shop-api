package com.example.click_n_shop_api.controller;

import com.example.click_n_shop_api.model.User;
import com.example.click_n_shop_api.request.RegisterRequest;
import com.example.click_n_shop_api.request.ResetPasswordRequest;
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
        User savedUser = userService.saveNewUser(registerReq);
        if(savedUser == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User with this email already exists.");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    @GetMapping("/fetch/{userUniqueId}")
    public ResponseEntity<?> getUserByUniqueId(@PathVariable String userUniqueId) {
        User user = userService.fetchUserByUniqueId(userUniqueId);
        if(user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with ID: " + userUniqueId + " not found!");
        }
        return ResponseEntity.ok(user);
    }

    @PatchMapping("/resetPassword")
    public ResponseEntity<?> updateUserPassword(@RequestBody ResetPasswordRequest resetPasswordReq) {
        User user = userService.resetPassword(resetPasswordReq);
        if(user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with ID: " + resetPasswordReq.getUserUniqueId() + " not found!");
        }
        return ResponseEntity.ok(user);
    }

    @GetMapping("/fetch/all")
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok(userService.fetchAllUsers());
    }
}
