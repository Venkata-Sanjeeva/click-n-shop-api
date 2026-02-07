package com.example.click_n_shop_api.controller;

import com.example.click_n_shop_api.exceptions.UserNotFoundException;
import com.example.click_n_shop_api.response.UsersOrdersResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.click_n_shop_api.model.Order;
import com.example.click_n_shop_api.request.OrdersRequest;
import com.example.click_n_shop_api.service.OrderService;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin
public class OrderController {

	@Autowired
	private OrderService orderService;
	
	@PostMapping("/save")
	public ResponseEntity<?> saveOrders(@RequestBody OrdersRequest ordersReq) {
		try {
			Order savedOrder = orderService.saveOrder(ordersReq);
			return ResponseEntity.ok(savedOrder);
		} catch (UserNotFoundException userNotFoundException) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with ID: " + ordersReq.getUserUniqueId() + " not found!");
		} catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error Saving Order Details..." + e.getMessage());
		}
	}

	@GetMapping("/fetch/{userUniqueId}")
	public ResponseEntity<?> fetchOrdersByUserID(@PathVariable String userUniqueId) {
		try {
			UsersOrdersResponse usersOrdersRes = orderService.fetchOrderByUserUniqueId(userUniqueId);
			return ResponseEntity.ok(usersOrdersRes);
		} catch (UserNotFoundException userNotFoundException) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with ID: " + userUniqueId + " not found!");
		} catch (Exception e) {
			return ResponseEntity.internalServerError().body("Error fetching orders for user..." + e.getMessage());
		}
	}
}
