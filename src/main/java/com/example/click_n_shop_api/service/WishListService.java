package com.example.click_n_shop_api.service;

import com.example.click_n_shop_api.exceptions.WishListProductNotFoundException;
import com.example.click_n_shop_api.repository.WishListRepository;
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
	private WishListProductRepository wishListProdRepo;

	@Autowired
	private WishListRepository wishListRepo;
	
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

			wishListProdRepo.save(wishListProd);
			
			return wishList;
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		return null;
	}

	public WishList fetchWishListByUserId(String userUniqueId) throws UserNotFoundException{
		User user = userService.fetchUserByUniqueId(userUniqueId);

		if(user == null) {
			throw new UserNotFoundException("User not found with ID: " + userUniqueId);
		}
		return wishListRepo.findByUserId(user.getId());
	}

	public WishList deleteWishListProduct(String uniqueUserId, String productId) throws UserNotFoundException{
		User user = userService.fetchUserByUniqueId(uniqueUserId);

		WishListProduct wishListProd = wishListProdRepo.findByProductIdAndWishListId(productId, user.getWishList().getId()).orElse(null);

		if(wishListProd == null) {
			throw new WishListProductNotFoundException("WishList Product with product ID: " + productId + " for user ID: " + uniqueUserId + " not found!");
		}

		wishListProdRepo.delete(wishListProd);

		return user.getWishList();
	}

}
