package com.example.click_n_shop_api.request;

import lombok.Data;

@Data
public class ProfileUpdateRequest {

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
    private String dob;
}
