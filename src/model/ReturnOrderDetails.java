package model;

public class ReturnOrderDetails {
    private String returnId;
    private String itemCode;
    private int qty;
    private double price;

    public ReturnOrderDetails() {
    }

    public ReturnOrderDetails(String returnId, String itemCode, int qty, double price) {
        this.returnId = returnId;
        this.itemCode = itemCode;
        this.qty = qty;
        this.price = price;
    }

    public String getReturnId() {
        return returnId;
    }

    public void setReturnId(String returnId) {
        this.returnId = returnId;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "ReturnOrderDetails{" +
                "returnId='" + returnId + '\'' +
                ", itemCode='" + itemCode + '\'' +
                ", qty=" + qty +
                ", price=" + price +
                '}';
    }
}
