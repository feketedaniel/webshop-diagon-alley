package com.codecool.shop.dao.mem;


import com.codecool.shop.dao.UserDao;
import com.codecool.shop.model.User;

import java.util.HashSet;
import java.util.Objects;
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
        int newId = data.size()+1;
        user.setId(newId);
        data.add(user);
        data.forEach(System.out::println);
    }

    @Override
    public User findByEmail(String email) {
        data.forEach(user -> System.out.println(user.getEmail()));
        return data
                .stream()
                .filter(user -> Objects.equals(user.getEmail(), email))
                .findAny().get();
    }

    @Override
    public boolean isRegistered(String email) {
        return data.stream()
                .anyMatch(user -> Objects.equals(user.getEmail(), email));
    }
}

