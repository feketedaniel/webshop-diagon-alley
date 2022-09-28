package com.codecool.shop.dao.mem;

import com.codecool.shop.dao.OrderDao;
import com.codecool.shop.model.Order;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class OrderDaoMem implements OrderDao {
    private static OrderDaoMem instance = null;
    private Set<Order> data = new HashSet<>();

    private OrderDaoMem() {
    }

    public static OrderDaoMem getInstance() {
        if (instance == null) {
            instance = new OrderDaoMem();
        }
        return instance;
    }

    @Override
    public void add(Order order) {
        int newID = data.size() + 1;
        order.setId(newID);
        order.setOrderItemsOrderId(newID);
        order.setPaymentDetailsOrderId(newID);
        data.add(order);

    }

    @Override
    public Order find(int id) {
        return data.stream().filter(t -> t.getId() == id).findFirst().orElse(null);
    }

    @Override
    public void remove(int id) {
        data.remove(find(id));
    }

    @Override
    public Set<Order> getAll() {
        return data;
    }

    @Override
    public Set<Order> getBy(int customerId) {
        return data.stream().filter(t -> t.getUserId() == (customerId)).collect(Collectors.toSet());
    }
}
