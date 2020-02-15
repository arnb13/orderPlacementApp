package com.example.order;

public class Order {
    private String email;
    private String product_name;
    private String amount;

    public Order(String email, String product_name, String amount) {
        this.email = email;
        this.product_name = product_name;
        this.amount = amount;
    }
}
