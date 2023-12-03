package com.uni.tech.dto;

public class LoginRequest {
    private String pin;
    private String password;
    public LoginRequest() {      }
    public String getPin() {
        return pin;
    }
    public void setPin(String pin) {
        this.pin = pin;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}