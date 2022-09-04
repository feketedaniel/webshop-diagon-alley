package com.codecool.shop.model;

public class OrderItem {
    protected int id;

    protected String name;
    protected int amount;

    public OrderItem(Product product) {
        this.id = product.id;
        this.name = product.name;
        this.amount = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
