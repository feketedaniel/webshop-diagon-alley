package com.codecool.shop.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Order {
    protected Integer id = null;
    protected UUID userId = null;
    protected PaymentDetails paymentDetails = null;
    protected boolean isChecked = false;
    protected boolean isPayed = false;
    protected Set<OrderItem> orderItems = new HashSet<>();

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

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
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

    public void subOrderItem(Product product) {
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

    public void removeOrderItem(Product product) {
        orderItems.stream()
                .filter(oi -> oi.getProductId() == product.getId())
                .findFirst().ifPresent(orderItem -> orderItems.remove(orderItem));

    }

    public void setPaymentDetailsOrderId(int orderId) {
        paymentDetails.setOrderId(orderId);
    }

    public void setOrderItemsOrderId(int orderId) {
        orderItems.forEach(orderItem -> orderItem.setOrderId(orderId));
    }

    @Override
    public String toString() {
        String order = "\n\tOrder Id: " + this.id +
                "\n\tCustomer Id: " + this.userId +
                "\n\tPayment Info: " + this.paymentDetails +
                "\n\tChecked: " + this.isChecked +
                "\n\tPayed: " + this.isPayed +
                "\n\tOrder Items: ";
        for (OrderItem orderItem : orderItems) {
            order += orderItem;
        }

        return order;
    }

}
