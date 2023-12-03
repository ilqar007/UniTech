package com.uni.tech.dto;

public class LoginResponse {
    private String pin;
    private String token;

    public LoginResponse(String pin, String token) {
        this.pin = pin;
        this.token = token;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}