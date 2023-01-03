package com.codecool.shop.model;

import java.util.UUID;

public class SessionUser {
    private UUID id;
    private String name;
    private String email;

    public SessionUser(UUID id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public SessionUser(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "\n\tUsername: " + name +
                "\n\tEmail: " + email +
                "\n\tId: " + id;
    }
}

