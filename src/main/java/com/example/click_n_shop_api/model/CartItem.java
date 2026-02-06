package com.example.click_n_shop_api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
public class CartItem {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JsonIgnore
	@ToString.Exclude             // Breaks the toString loop
    @EqualsAndHashCode.Exclude    // Breaks the hashCode loop
	private Cart cart;
	
	@Column(unique = true)
    private String productId;
	
    private Integer quantity;
    private Double price;
}
