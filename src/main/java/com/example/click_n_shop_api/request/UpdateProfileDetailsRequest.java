package com.example.click_n_shop_api.request;

import java.sql.Date;

import com.example.click_n_shop_api.model.User;
import lombok.Data;

@Data
public class UpdateProfileDetailsRequest {
	public UpdateProfileDetailsRequest() {}

	public UpdateProfileDetailsRequest(User user) {
		email = user.getEmail();
		fullName = user.getFullName();
		phone = user.getPhone();
		address = user.getAddress();
		country = user.getCountry();
		state = user.getState();
		city = user.getCity();
		zipcode = user.getZipcode();
		gender = user.getGender();
		dob = user.getDob();
	}

	private String email;
	private String fullName;
	private String phone;
	private String address;
	private String city;
	private String country;
	private String state;
	private String zipcode;
	private String gender;
	private Date dob;
}
