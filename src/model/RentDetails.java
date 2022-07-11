package model;

import java.time.LocalDate;

public class RentDetails {
    private String rentId;
    private String itemCode;
    private int qty;
    private int rentDays;
    private LocalDate returnDate;
    private double price;
    private String status;

    public RentDetails() {
    }

    public RentDetails(String rentId, String itemCode, int qty, int rentDays, LocalDate returnDate, double price, String status) {
        this.rentId = rentId;
        this.itemCode = itemCode;
        this.qty = qty;
        this.rentDays = rentDays;
        this.returnDate = returnDate;
        this.price = price;
        this.status = status;
    }

    public String getRentId() {
        return rentId;
    }

    public void setRentId(String rentId) {
        this.rentId = rentId;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public int getRentDays() {
        return rentDays;
    }

    public void setRentDays(int rentDays) {
        this.rentDays = rentDays;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "RentDetails{" +
                "rentId='" + rentId + '\'' +
                ", itemCode='" + itemCode + '\'' +
                ", qty=" + qty +
                ", rentDays=" + rentDays +
                ", returnDate=" + returnDate +
                ", price=" + price +
                ", status='" + status + '\'' +
                '}';
    }
}
