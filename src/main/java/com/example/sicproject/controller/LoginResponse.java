package com.example.sicproject.controller;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatusCode;

@Getter
@Setter
public class LoginResponse  {
    private String username;
    private String token;
    private long id;

    public LoginResponse(long id,String username, String token) {
        this.username = username;
        this.token = token;
        this.id=id;
    }

    // Getters
}