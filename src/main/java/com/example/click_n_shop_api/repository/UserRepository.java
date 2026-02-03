package com.example.click_n_shop_api.repository;

import com.example.click_n_shop_api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // Custom query to find a user by their username
    Optional<User> findByUsername(String username);

    // Custom query to find a user by their user unique Id
    Optional<User> findByUniqueId(String userUniqueId);

    // Custom query to check if an email already exists
    Boolean existsByEmail(String email);
}
