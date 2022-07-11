package view.TM;

import javafx.scene.control.Button;

public class ReturnDetailsTM {
    private String ReturnId;
    private String date;
    private double cost;
    private Button btnDetails;

    public ReturnDetailsTM() {
    }

    public ReturnDetailsTM(String returnId, String date, double cost, Button btnDetails) {
        ReturnId = returnId;
        this.date = date;
        this.cost = cost;
        this.btnDetails = btnDetails;
    }

    public String getReturnId() {
        return ReturnId;
    }

    public void setReturnId(String returnId) {
        ReturnId = returnId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public Button getBtnDetails() {
        return btnDetails;
    }

    public void setBtnDetails(Button btnDetails) {
        this.btnDetails = btnDetails;
    }

    @Override
    public String toString() {
        return "ReturnDetailsTM{" +
                "ReturnId='" + ReturnId + '\'' +
                ", date='" + date + '\'' +
                ", cost=" + cost +
                ", btnDetails=" + btnDetails +
                '}';
    }
}
