package com.codecool.shop.model;

import java.util.Date;

public class PaymentDetails {
    private static int nextId=0;
    private int id;
    private int orderId;
    private Integer userId;
    private String shippingCountry;
    private String shippingCity;
    private int shippingZip;
    private String shippingStreetHouseNum;

    private String name;
    private String email;
    private String phone;

    private String billingCountry;
    private String billingCity;
    private int billingZip;
    private String billingStreetHouseNum;
    private Date payDate;


    public PaymentDetails(){
        this.id = nextId++;
    }

    public Date getPayDate() {
        return payDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getShippingCountry() {
        return shippingCountry;
    }

    public void setShippingCountry(String shippingCountry) {
        this.shippingCountry = shippingCountry;
    }

    public String getShippingCity() {
        return shippingCity;
    }

    public void setShippingCity(String shippingCity) {
        this.shippingCity = shippingCity;
    }

    public int getShippingZip() {
        return shippingZip;
    }

    public void setShippingZip(int shippingZip) {
        this.shippingZip = shippingZip;
    }

    public String getShippingStreetHouseNum() {
        return shippingStreetHouseNum;
    }

    public void setShippingStreetHouseNum(String shippingStreetHouseNum) {
        this.shippingStreetHouseNum = shippingStreetHouseNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBillingCountry() {
        return billingCountry;
    }

    public void setBillingCountry(String billingCountry) {
        this.billingCountry = billingCountry;
    }

    public String getBillingCity() {
        return billingCity;
    }

    public void setBillingCity(String billingCity) {
        this.billingCity = billingCity;
    }

    public int getBillingZip() {
        return billingZip;
    }

    public void setBillingZip(int billingZip) {
        this.billingZip = billingZip;
    }

    public String getBillingStreetHouseNum() {
        return billingStreetHouseNum;
    }

    public void setBillingStreetHouseNum(String billingStreetHouseNum) {
        this.billingStreetHouseNum = billingStreetHouseNum;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    @Override
    public String toString() {
        return "\n\t\tPayment id: "+id+
                "\n\t\tOrder id: "+orderId+
                "\n\t\tUser id: "+userId+
                "\n\t\tShipping details: "+
                "\n\t\t\tCountry/City: "+shippingCountry+", "+shippingCity+
                "\n\t\t\tAdress/Zip: "+shippingStreetHouseNum+", "+shippingZip+
                "\n\t\tBilling details: "+
                "\n\t\t\tCountry/City: "+billingCountry+", "+billingCity+
                "\n\t\t\tAdress/Zip: "+billingStreetHouseNum+", "+billingZip+
                "\n\t\tBuyer info:"+
                "\n\t\t\t"+name+", "+email+", "+phone;
    }
}
