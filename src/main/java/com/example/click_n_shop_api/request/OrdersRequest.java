package com.example.click_n_shop_api.request;

import java.util.List;

import com.example.click_n_shop_api.model.ProductOrderDetails;

import lombok.Data;

@Data
public class OrdersRequest {

	private String userUniqueId;
	private String orderUniqueId;
	
	private List<ProductOrderDetails> listOfProducts;
}
