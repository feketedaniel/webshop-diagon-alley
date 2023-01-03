package com.codecool.shop.dao;

import com.codecool.shop.model.User;

import java.util.Optional;
import java.util.Set;

public interface UserDao {
    boolean isRegistered(String email);

    void add(User user);

    Optional<User> findByEmail(String email);

    Set<User> getAllUser();
}
