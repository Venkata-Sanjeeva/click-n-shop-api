package com.example.click_n_shop_api.request;

import java.sql.Date;

import lombok.Data;

@Data
public class UpdateProfileDetailsRequest {
	
	private String fullName;
	private String phone;
	private String address;
	private String city;
	private String zipcode;
	private String gender;
	private Date dob;
}
