package view.TM;

public class SupplierOrderTM {
    private String id;
    private String date;
    private String time;
    private String supplierId;
    private double cost;

    public SupplierOrderTM() {
    }

    public SupplierOrderTM(String id, String date, String time, String supplierId, double cost) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.supplierId = supplierId;
        this.cost = cost;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "SupplierOrderTM{" +
                "id='" + id + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", supplierId='" + supplierId + '\'' +
                ", cost=" + cost +
                '}';
    }
}
