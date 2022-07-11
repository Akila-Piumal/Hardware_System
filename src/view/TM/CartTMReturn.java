package view.TM;

import javafx.scene.control.Button;

public class CartTMReturn {
    private String itemCode;
    private String description;
    private double unitPrice;
    private int returnQty;
    private double totalCost;
    private Button btnDelete;

    public CartTMReturn() {
    }

    public CartTMReturn(String itemCode, String description, double unitPrice, int returnQty, double totalCost, Button btnDelete) {
        this.itemCode = itemCode;
        this.description = description;
        this.unitPrice = unitPrice;
        this.returnQty = returnQty;
        this.totalCost = totalCost;
        this.btnDelete = btnDelete;
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

    public int getReturnQty() {
        return returnQty;
    }

    public void setReturnQty(int returnQty) {
        this.returnQty = returnQty;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public Button getBtnDelete() {
        return btnDelete;
    }

    public void setBtnDelete(Button btnDelete) {
        this.btnDelete = btnDelete;
    }

    @Override
    public String toString() {
        return "CartTMReturn{" +
                "itemCode='" + itemCode + '\'' +
                ", description='" + description + '\'' +
                ", unitPrice=" + unitPrice +
                ", returnQty=" + returnQty +
                ", totalCost=" + totalCost +
                ", btnDelete=" + btnDelete +
                '}';
    }
}
