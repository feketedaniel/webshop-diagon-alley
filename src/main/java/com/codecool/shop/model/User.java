package com.codecool.shop.model;

public class User {
    protected int id;
    protected String name;
    protected String email;
    protected byte[] password;
    protected byte[] salt;
    public User(String name, String email, byte[]password, byte[]salt){

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
