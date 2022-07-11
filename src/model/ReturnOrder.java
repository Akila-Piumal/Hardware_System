package model;

import java.time.LocalDate;

public class ReturnOrder {
    private String id;
    private LocalDate date;
    private double cost;

    public ReturnOrder() {
    }

    public ReturnOrder(String id, LocalDate date, double cost) {
        this.id = id;
        this.date = date;
        this.cost = cost;
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

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "ReturnOrder{" +
                "id='" + id + '\'' +
                ", date=" + date +
                ", cost=" + cost +
                '}';
    }
}
