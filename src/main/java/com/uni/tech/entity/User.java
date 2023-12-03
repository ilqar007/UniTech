package com.uni.tech.entity;

import jakarta.persistence.*;


@Entity
@Table(name="users")
public class User {

    public User()
    {}
    public  User(String pin)
    {
      this.pin=pin;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String pin;

    @Column(nullable = false)
    private String password;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getPin() {
        return pin;
    }
    public void setPin(String username) {
        this.pin = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

}