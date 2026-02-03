package com.example.click_n_shop_api.response;

import com.example.click_n_shop_api.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class GetAllUsersResponse {
    private List<User> allUsersList;
}
