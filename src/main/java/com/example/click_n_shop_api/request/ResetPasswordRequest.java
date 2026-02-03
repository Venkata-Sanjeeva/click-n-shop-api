package com.example.click_n_shop_api.request;

import lombok.Data;

@Data
public class ResetPasswordRequest {
    String userUniqueId;
    String password;
}
