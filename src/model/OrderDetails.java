package model;

public class OrderDetails {
    private String orderId;
    private String itemCode;
    private int qty;
    private double price;

    public OrderDetails() {
    }

    public OrderDetails(String orderId, String itemCode, int qty, double price) {
        this.orderId = orderId;
        this.itemCode = itemCode;
        this.qty = qty;
        this.price = price;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
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
        return "OrderDetails{" +
                "orderId='" + orderId + '\'' +
                ", itemCode='" + itemCode + '\'' +
                ", qty=" + qty +
                ", price=" + price +
                '}';
    }
}
