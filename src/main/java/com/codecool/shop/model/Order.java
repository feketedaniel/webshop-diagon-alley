package com.codecool.shop.model;

import java.util.*;

public class Order {
    protected int id;
    protected int userId;
    protected PaymentDetails paymentDetails;
    protected boolean isChecked = false;
    protected boolean isPayed = false;
    protected Set<OrderItem> orderItems;

    public Order(){
        this.orderItems = new HashSet<>();
    }

    public Set<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(Set<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public boolean isPayed() {
        return isPayed;
    }

    public void setPayed(boolean payed) {
        isPayed = payed;
 /*TODO:delete line on live*/       paymentDetails = new PaymentDetails();
        paymentDetails.setPayDate(new Date());
    }

    public PaymentDetails getPaymentDetails() {
        return paymentDetails;
    }

    public void setPaymentDetails(PaymentDetails paymentDetails) {
        this.paymentDetails = paymentDetails;
    }


    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void addOrderItem(Product product) {
        OrderItem orderItem = orderItems.stream()
                .filter(oi -> oi.getProductId() == product.getId())
                .findFirst()
                .orElse(new OrderItem(product));
        orderItem.setAmount(orderItem.getAmount() + 1);
        orderItems.add(orderItem);
    }

    public void subOrderItem(Product product){
        orderItems
                .stream()
                .filter(oi -> oi.getProductId() == product.getId())
                .findFirst()
                .ifPresent(orderItem -> {
                    orderItem.setAmount(orderItem.getAmount() - 1);
                    if (orderItem.getAmount() <= 0) {
                        orderItems.remove(orderItem);
                    }
                });
    }

    public void removeOrderItem(Product product){
        orderItems.stream()
                .filter(oi -> oi.getProductId() == product.getId())
                .findFirst().ifPresent(orderItem -> orderItems.remove(orderItem));

    }

    @Override
    public String toString() {
        return "\nOrder Id: " + this.id + "\n" +
                "Customer Id: " + this.userId + "\n" +
                "Payment Info Id: " + this.paymentDetails + "\n" +
                "Checked: " + this.isChecked + "\n" +
                "Payed: " + this.isPayed + "\n";
    }

}
