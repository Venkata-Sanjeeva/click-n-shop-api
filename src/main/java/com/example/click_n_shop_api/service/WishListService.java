package com.example.click_n_shop_api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.click_n_shop_api.exceptions.UserNotFoundException;
import com.example.click_n_shop_api.model.User;
import com.example.click_n_shop_api.model.WishList;
import com.example.click_n_shop_api.model.WishListProduct;
import com.example.click_n_shop_api.repository.WishListProductRepository;
import com.example.click_n_shop_api.request.AddWishListRequest;

@Service
public class WishListService {

	@Autowired
	private WishListProductRepository wishListRepo;
	
	@Autowired
	private UserService userService;
	
	public WishList saveWishListProduct(AddWishListRequest addWishListReq) {
		User user = userService.fetchUserByUniqueId(addWishListReq.getUserUniqueId());
		
		try {
			if(user == null) {
				throw new UserNotFoundException("User not found with ID: " + addWishListReq.getUserUniqueId());
			}
			
			WishList wishList = user.getWishList();
			
			WishListProduct wishListProd = addWishListReq.getWishListProduct();
			
			wishListProd.setWishList(wishList);
			
			wishListRepo.save(wishListProd);
			
			return wishList;
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		return null;
	}
}
