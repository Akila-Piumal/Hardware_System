package model;

import java.time.LocalDate;

public class SupplyOrder {
    private String id;
    private LocalDate date;
    private String time;
    private double totalCost;
    private String supplierId;

    public SupplyOrder() {
    }

    public SupplyOrder(String id, LocalDate date, String time, double totalCost, String supplierId) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.totalCost = totalCost;
        this.supplierId = supplierId;
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

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    @Override
    public String toString() {
        return "SupplyOrder{" +
                "id='" + id + '\'' +
                ", date=" + date +
                ", time='" + time + '\'' +
                ", totalCost=" + totalCost +
                ", supplierId='" + supplierId + '\'' +
                '}';
    }
}
