package com.example.click_n_shop_api.response;

import com.example.click_n_shop_api.model.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsersOrdersResponse {
    private List<Order> ordersList;
}
