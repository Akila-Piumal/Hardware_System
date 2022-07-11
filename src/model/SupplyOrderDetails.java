package model;

public class SupplyOrderDetails {
    private String orderId;
    private String itemId;
    private int qty;
    private double price;

    public SupplyOrderDetails() {
    }

    public SupplyOrderDetails(String orderId, String itemId, int qty, double price) {
        this.orderId = orderId;
        this.itemId = itemId;
        this.qty = qty;
        this.price = price;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
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
        return "SupplyOrderDetails{" +
                "orderId='" + orderId + '\'' +
                ", itemId='" + itemId + '\'' +
                ", qty=" + qty +
                ", price=" + price +
                '}';
    }
}
