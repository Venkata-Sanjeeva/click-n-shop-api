package com.example.click_n_shop_api.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "orders")
@Data
public class Order {
    @Id
    private Long orderId;

    private String orderDate;
    private String dueDate;
    private Double totalPrice;

    @ElementCollection
    @CollectionTable(name = "order_products", joinColumns = @JoinColumn(name = "order_id"))
    private List<ProductOrderDetails> productsList;
}

@Embeddable
@Data
class ProductOrderDetails {
    private String productId;
    private Integer quantity;
}