package com.example.selethen.billreminder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Bill {
    private String title;
    private String description;
    private double price;
    private Date date;

    public Bill(String title, String description, double price, String date) throws ParseException {
        this.title = title;
        this.description = description;
        this.price = price;
        this.date = stringToDate(date);
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

    private Date stringToDate(String dateString) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        return sdf.parse(dateString);
    }
}
