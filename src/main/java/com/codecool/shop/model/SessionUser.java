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
}

