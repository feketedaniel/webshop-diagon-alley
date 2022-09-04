package com.codecool.shop.model;

import java.math.BigDecimal;

public class OrderItem {
    protected int id;

    protected String name;
    protected int amount;
    protected BigDecimal defaultPrice;

    public OrderItem(Product product) {
        this.id = product.id;
        this.name = product.name;
        this.defaultPrice = product.getDefaultPrice();
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
