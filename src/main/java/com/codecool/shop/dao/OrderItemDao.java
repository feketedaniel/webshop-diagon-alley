package com.codecool.shop.dao;

import com.codecool.shop.model.base.OrderItem;

import java.util.List;

public interface OrderItemDao {
    void add(OrderItem orderItem);
    OrderItem find(int id);
    void remove(int id);
    List<OrderItem> getAll();
    List<OrderItem> getBy(int orderId);
}
