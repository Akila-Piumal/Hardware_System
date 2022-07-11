package view.TM;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

import java.time.LocalDate;

public class RentDetailsTM {
    private String rentId;
    private String date;
    private String time;
    private double totalCost;
    private String customerId;
    private Button btnDetails;
    private Button option;

    public RentDetailsTM() {
    }

    public RentDetailsTM(String rentId, String date, String time, double totalCost, String customerId, Button btnDetails, Button option) {
        this.rentId = rentId;
        this.date = date;
        this.time = time;
        this.totalCost = totalCost;
        this.customerId = customerId;
        this.btnDetails = btnDetails;
        this.option = option;
    }

    public String getRentId() {
        return rentId;
    }

    public void setRentId(String rentId) {
        this.rentId = rentId;
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

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public Button getBtnDetails() {
        return btnDetails;
    }

    public void setBtnDetails(Button btnDetails) {
        this.btnDetails = btnDetails;
    }

    public Button getOption() {
        return option;
    }

    public void setOption(Button option) {
        this.option = option;
    }

    @Override
    public String toString() {
        return "RentDetailsTM{" +
                "rentId='" + rentId + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", totalCost=" + totalCost +
                ", customerId='" + customerId + '\'' +
                ", btnDetails=" + btnDetails +
                ", option=" + option +
                '}';
    }
}
