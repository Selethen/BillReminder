package com.example.selethen.billreminder;

import java.util.Date;

public class Bill {
    private String title;
    private String description;
    private double price;
    private Date date;

    public Bill(String title, String description, double price, Date date) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public double getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }
}
