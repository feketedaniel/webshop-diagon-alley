package com.codecool.shop.model.base;

import com.codecool.shop.model.info.AddressInfo;
import com.codecool.shop.model.info.CustomerInfo;

import java.util.HashSet;
import java.util.Set;

public class Order {
    protected int id;
    protected int customerId;
    protected Set<OrderItem> orderItems;
    protected boolean isChecked = false;
    protected boolean isPayed = false;
    protected AddressInfo billingInformation;
    protected AddressInfo shippingInformation;
    protected CustomerInfo customerInformation;

    public Order() {
        this.orderItems = new HashSet<>() {
        };
    }


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

    public Set<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(Set<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public void addProduct(Product product, Order order) {
        OrderItem orderItem = order.orderItems
                .stream()
                .filter(oi -> oi.orderId == order.id && oi.productId == product.id)
                .findFirst()
                .orElse(new OrderItem(product, order));
        orderItem.amount += 1;
        orderItems.add(orderItem);
    }

    public void subProduct(Product product, Order order) {
        order.orderItems
                .stream()
                .filter(oi -> oi.orderId == order.id && oi.productId == product.id)
                .findFirst()
                .ifPresent(orderItem -> {
                    orderItem.amount += -1;
                    if (orderItem.amount <= 0) {
                        orderItems.remove(orderItem);
                    }
                });
    }

    public void removeOrderItem(int prodId, Order order) {
        orderItems
                .stream()
                .filter(oi -> oi.orderId == order.id && oi.productId == prodId)
                .findFirst().ifPresent(orderItem -> orderItems.remove(orderItem));
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
        orderItems.forEach((orderItem) -> {
            sb.append("     Product name: ").append(orderItem.name).append(", Amount: ").append(orderItem.amount).append("\n");
        });
        return sb.toString();
    }

}
