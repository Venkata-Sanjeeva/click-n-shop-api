package com.example.click_n_shop_api.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class WishList {
    @Id
    private Long id;

    @ElementCollection
    @CollectionTable(name = "wishlist_products", joinColumns = @JoinColumn(name = "wishlist_id"))
    private List<WishListProduct> wishListProducts;
}

@Embeddable
@Data
class WishListProduct {
    private String productId;
}