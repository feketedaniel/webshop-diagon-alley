package com.codecool.shop.model;

import java.util.HashMap;
import java.util.Map;

public class Order {
    private static int nextId = 0;
    private int id;
    private int customerId;
    private Map<Integer, OrderItem> orderItems;
    private boolean isChecked = false;
    private boolean isPayed =false;
    private AddressInfo billingInformation;
    private AddressInfo shippingInformation;
    private CustomerInfo customerInformation;


    public AddressInfo getBillingInformation() {
        return billingInformation;
    }

    public void setBillingInformation(AddressInfo billingInformation) {
        this.billingInformation = billingInformation;
    }

    public AddressInfo getShippingInformation() {
        return shippingInformation;
    }

    public void setShippingInformation(AddressInfo shippingInformation) {
        this.shippingInformation = shippingInformation;
    }

    public CustomerInfo getCustomerInformation() {
        return customerInformation;
    }

    public void setCustomerInformation(CustomerInfo customerInformation) {
        this.customerInformation = customerInformation;
    }


    public boolean isPayed() {
        return isPayed;
    }

    public void setPayed(boolean payed) {
        isPayed = payed;
    }


    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

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

    public Map<Integer, OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(Map<Integer, OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public void addProduct(Product product) {
        OrderItem orderItem = orderItems.getOrDefault(product.id, new OrderItem(product));
        orderItem.amount += 1;
        orderItems.put(product.id, orderItem);
    }

    public void subProduct(Product product) {
        OrderItem orderItem = orderItems.getOrDefault(product.id, new OrderItem(product));
        orderItem.amount += -1;
        if (orderItem.amount == 0) {
            orderItems.remove(product.id);
        } else {
            orderItems.put(product.id, orderItem);
        }
    }

    public void removeOrderItem(int prodId) {
        orderItems.remove(prodId);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("\nOrder Id: ").append(this.id).append("\n");
        sb.append("Customer Id: ").append(this.customerId).append("\n");
        sb.append("Customer Info: ").append(this.customerInformation).append("\n");
        sb.append("Checked: ").append(this.isChecked).append("\n");
        sb.append("Payed: ").append(this.isPayed).append("\n");
        sb.append("BillingInfo: ").append(this.billingInformation).append("\n");
        sb.append("ShippingInfo: ").append(this.shippingInformation).append("\n");
        sb.append("OrderItems: \n");
        orderItems.forEach((productId, orderItem) -> {
            sb.append("     Product name: ").append(orderItem.name).append(", Amount: ").append(orderItem.amount).append("\n");
        });
        return sb.toString();
    }

}
