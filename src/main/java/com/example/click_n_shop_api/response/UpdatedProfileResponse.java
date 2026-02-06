package com.example.click_n_shop_api.response;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdatedProfileResponse {
	private String uniqueId;
    private String fullName;
    private String email;
    private String phone;
    private String address;
    private String city;
    private String country;
    private String zipcode;
    private String state;
    private String gender;
    private Date dob;
}
