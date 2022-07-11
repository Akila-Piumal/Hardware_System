package view.TM;

import javafx.scene.control.Button;

public class CartTMSupply {
    private String code;
    private String description;
    private double buyingPrice;
    private double sellingPrice;
    private int qty;
    private double totalCost;
    private Button btn;

    public CartTMSupply() {
    }

    public CartTMSupply(String code, String description, double buyingPrice, double sellingPrice, int qty, double totalCost, Button btn) {
        this.code = code;
        this.description = description;
        this.buyingPrice = buyingPrice;
        this.sellingPrice = sellingPrice;
        this.qty = qty;
        this.totalCost = totalCost;
        this.btn = btn;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getBuyingPrice() {
        return buyingPrice;
    }

    public void setBuyingPrice(double buyingPrice) {
        this.buyingPrice = buyingPrice;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
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
        return "CartTMSupply{" +
                "code='" + code + '\'' +
                ", description='" + description + '\'' +
                ", buyingPrice=" + buyingPrice +
                ", sellingPrice=" + sellingPrice +
                ", qty=" + qty +
                ", totalCost=" + totalCost +
                ", btn=" + btn +
                '}';
    }
}
