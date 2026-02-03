package com.example.click_n_shop_api.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "cart") // Refers to the 'cart' field in the User class
    private User user;

    @ElementCollection
    /*
    When you use this, JPA creates a separate table for the collection behind the scenes,
        but it manages the lifecycle of those items automatically.
    If you delete the User, the items in the collection are deleted too.
     */
    @CollectionTable(name = "cart_items", joinColumns = @JoinColumn(name = "cart_id"))
    private List<CartItem> cartItems;
}

/*
Annotation,Purpose,Key Characteristic
@Entity,Marks a class as a primary table in the DB.,Must have a unique @Id.
@Embeddable,Marks a class that will be stored as part of another table/entity.,Does not have its own @Id.
@ElementCollection,Tells JPA to store a List/Set of simple objects in a separate table.,Items are deleted if the parent is deleted.
@JoinColumn,Defines the physical column used to link two tables.,"The ""Foreign Key"" column."
 */
