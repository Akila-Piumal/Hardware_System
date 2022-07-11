package view.TM;

public class OrderTM {
    private String orderId;
    private String date;
    private String time;
    private String customerId;
    private double cost;

    public OrderTM() {
    }

    public OrderTM(String orderId, String date, String time, String customerId, double cost) {
        this.orderId = orderId;
        this.date = date;
        this.time = time;
        this.customerId = customerId;
        this.cost = cost;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "OrderTM{" +
                "orderId='" + orderId + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", customerId='" + customerId + '\'' +
                ", cost=" + cost +
                '}';
    }
}
