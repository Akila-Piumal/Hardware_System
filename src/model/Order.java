package model;

import java.time.LocalDate;
import java.util.Date;

public class Order {
    private String id;
    private LocalDate date;
    private String time;
    private double totalCost;
    private String customerId;

    public Order() {
    }

    public Order(String id, LocalDate date, String time, double totalCost, String customerId) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.totalCost = totalCost;
        this.customerId = customerId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }


}
