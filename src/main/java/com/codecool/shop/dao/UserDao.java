package com.codecool.shop.dao;

import com.codecool.shop.model.User;

public interface UserDao {
    boolean isRegistered(String email);
    void add(User user);

    User findByEmail(String email);
}
