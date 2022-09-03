package com.codecool.shop.model;

import java.util.HashMap;
import java.util.Map;

public class Order {
    private static int nextId = 0;
    protected int id;
    protected int customerId;
    protected Map<Product, Integer> orderItems;

    public Order() {
        this.id = nextId++;
        this.orderItems = new HashMap<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public Map<Product, Integer> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(Map<Product, Integer> orderItems) {
        this.orderItems = orderItems;
    }

    public void addProduct(Product product){
        int newAmount = orderItems.getOrDefault(product,0)+1;
        orderItems.put(product,newAmount);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("\nOrder Id: ").append(this.id).append("\n");
        sb.append("Customer Id: ").append(this.customerId).append("\n");
        orderItems.forEach((product, amount) -> {
            sb.append("Product name: ").append(product.name).append(", Amount: ").append(amount).append("\n");
        });
        return sb.toString();
    }
}
