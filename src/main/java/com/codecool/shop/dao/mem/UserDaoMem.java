package com.codecool.shop.dao.mem;


import com.codecool.shop.dao.UserDao;
import com.codecool.shop.model.User;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public class UserDaoMem implements UserDao {
    private static UserDaoMem instance = null;
    private Set<User> data = new HashSet<>();

    public static UserDaoMem getInstance() {
        if (instance==null){
            instance = new UserDaoMem();
        }
        return instance;
    }

    public void add(User user){
        data.add(user);
    }

    @Override
    public User findByEmail(String email) {
        return data
                .stream()
                .filter(user -> Objects.equals(user.getEmail(), email))
                .findAny().orElseGet(null);
    }

    @Override
    public boolean isRegistered(String email) {
        return data.stream()
                .anyMatch(user -> Objects.equals(user.getEmail(), email));
    }

    @Override
    public Set<User> getAllUser() {
        return data;
    }
}

