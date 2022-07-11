package view.TM;

import javafx.scene.layout.HBox;

public class ItemTM {
    private String code;
    private String description;
    private double buyingPrice;
    private double sellingPrice;
    private int qtyOnHand;
    private String status;
    private HBox box;

    public ItemTM() {
    }

    public ItemTM(String code, String description, double buyingPrice, double sellingPrice, int qtyOnHand, String status, HBox box) {
        this.code = code;
        this.description = description;
        this.buyingPrice = buyingPrice;
        this.sellingPrice = sellingPrice;
        this.qtyOnHand = qtyOnHand;
        this.status = status;
        this.box = box;
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

    public int getQtyOnHand() {
        return qtyOnHand;
    }

    public void setQtyOnHand(int qtyOnHand) {
        this.qtyOnHand = qtyOnHand;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public HBox getBox() {
        return box;
    }

    public void setBox(HBox box) {
        this.box = box;
    }

    @Override
    public String toString() {
        return "ItemTM{" +
                "code='" + code + '\'' +
                ", description='" + description + '\'' +
                ", buyingPrice=" + buyingPrice +
                ", sellingPrice=" + sellingPrice +
                ", qtyOnHand=" + qtyOnHand +
                ", status='" + status + '\'' +
                ", box=" + box +
                '}';
    }
}
