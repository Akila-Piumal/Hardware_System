package view.TM;

import javafx.scene.control.Button;

public class CartTMRent {
    private String itemCode;
    private String description;
    private double unitPrice;
    private int qty;
    private int rentDays;
    private double totalCost;
    private Button btn;

    public CartTMRent() {
    }

    public CartTMRent(String itemCode, String description, double unitPrice, int qty, int rentDays, double totalCost, Button btn) {
        this.itemCode = itemCode;
        this.description = description;
        this.unitPrice = unitPrice;
        this.qty = qty;
        this.rentDays = rentDays;
        this.totalCost = totalCost;
        this.btn = btn;
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

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public Button getBtn() {
        return btn;
    }

    public void setBtn(Button btn) {
        this.btn = btn;
    }

    @Override
    public String toString() {
        return "RentTM{" +
                "itemCode='" + itemCode + '\'' +
                ", description='" + description + '\'' +
                ", unitPrice=" + unitPrice +
                ", qty=" + qty +
                ", rentDays=" + rentDays +
                ", totalCost=" + totalCost +
                ", btn=" + btn +
                '}';
    }
}
