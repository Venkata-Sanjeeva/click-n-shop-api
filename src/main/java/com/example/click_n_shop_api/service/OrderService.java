package com.example.click_n_shop_api.service;

import com.example.click_n_shop_api.exceptions.UserNotFoundException;
import com.example.click_n_shop_api.model.*;
import com.example.click_n_shop_api.repository.OrderRepository;
import com.example.click_n_shop_api.repository.ProductOrderDetailsRepository;
import com.example.click_n_shop_api.request.OrdersRequest;
import com.example.click_n_shop_api.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserService userService;

    @Transactional // Let Spring handle the rollback automatically if an exception occurs
    public Order saveOrder(OrdersRequest ordersReq) {
        // 1. Fetch User
        User user = userService.fetchUserByUniqueId(ordersReq.getUserUniqueId());
        
        // 2. Validate User (If null, throwing here triggers a clean rollback)
        if (user == null) {
            throw new UserNotFoundException("User not found with ID: " + ordersReq.getUserUniqueId());
        }
        
        Order order = new Order();
        
        // 3. Set Dates
        LocalDate localDate = LocalDate.now();
        order.setOrderDate(Date.valueOf(localDate));
        order.setDueDate(Date.valueOf(localDate.plusDays(7)));
        order.setUser(user);

        // 4. Map Products and Link back to Order
        List<ProductOrderDetails> items = ordersReq.getListOfProducts();
        for (ProductOrderDetails item : items) {
            item.setOrder(order);
        }
        
        order.setProductsList(items);
        order.setTotalPrice(calculateTotal(items));

        // 5. Save (Cascades automatically to ProductOrderDetails)
        return orderRepository.save(order);
    }

    private Double calculateTotal(List<ProductOrderDetails> items) {
        return items.stream()
                .mapToDouble(item -> (item.getPrice() != null ? item.getPrice() : 0.0) * item.getQuantity())
                .sum();
    }
}