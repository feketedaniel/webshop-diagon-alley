package com.codecool.shop.model;

import java.util.UUID;

public class User {
    private UUID id;
    private String name;
    private String email;
    private byte[] password;
    private byte[] salt;
    public User(String name, String email, byte[]password, byte[]salt){
        this.id = UUID.randomUUID();
        this.name = name;
        this.email = email;

    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
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
