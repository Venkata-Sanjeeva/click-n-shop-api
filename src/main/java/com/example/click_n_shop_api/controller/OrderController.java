package com.example.click_n_shop_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
		Order savedOrder = orderService.saveOrder(ordersReq);
		if(savedOrder == null) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error Saving Order Details...");
		}
		return ResponseEntity.ok(savedOrder);
	}
}
