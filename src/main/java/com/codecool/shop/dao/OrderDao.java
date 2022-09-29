package com.codecool.shop.dao;

import com.codecool.shop.model.Order;

import java.util.Set;
import java.util.UUID;

public interface OrderDao {
    void add(Order order);

    Order find(int id);

    void remove(int id);

    Set<Order> getAll();

    Set<Order> getBy(UUID customerId);

}
