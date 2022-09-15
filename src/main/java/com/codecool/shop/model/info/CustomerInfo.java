package com.codecool.shop.model.info;

public class CustomerInfo {
    private String name;
    private String email;
    private String phone;

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

    public CustomerInfo(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("\nName: ").append(this.name);
        sb.append("\nE-Mail: ").append(this.email);
        sb.append("\nPhone Nr.: ").append(this.phone);
        return sb.toString();    }

}
