package view.TM;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class RentItemDetailsTM {
    private String itemCode;
    private String description;
    private double unitPrice;
    private int qty;
    private int rentDays;
    private String returnDate;
    private double cost;
    private HBox box;

    public RentItemDetailsTM() {
    }

    public RentItemDetailsTM(String itemCode, String description, double unitPrice, int qty, int rentDays, String returnDate, double cost, HBox box) {
        this.itemCode = itemCode;
        this.description = description;
        this.unitPrice = unitPrice;
        this.qty = qty;
        this.rentDays = rentDays;
        this.returnDate = returnDate;
        this.cost = cost;
        this.box = box;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
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

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public HBox getBox() {
        return box;
    }

    public void setBox(HBox box) {
        this.box = box;
    }
}
