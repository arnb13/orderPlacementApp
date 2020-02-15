package com.example.order;

public class User {
    private Integer uid;
    private String full_name;
    private String email;
    private String phone;
    private String password;
    private String address;
    private String postal_code;

    public User(String full_name, String email, String phone, String password, String address, String postal_code) {
        this.full_name = full_name;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.address = address;
        this.postal_code = postal_code;
    }
}
