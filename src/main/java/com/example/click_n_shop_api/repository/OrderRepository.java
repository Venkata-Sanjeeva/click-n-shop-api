package com.example.click_n_shop_api.repository;

import com.example.click_n_shop_api.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    // Find all orders that are due on a specific date
    List<Order> findByDueDate(String dueDate);
    List<Order> findByUserId(Long userId);
}
