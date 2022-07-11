package controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import util.LoadDateAndTime;

import java.io.IOException;

public class CashierDashBoardFormController {
    public static String userName;
    public AnchorPane dashBoardContext;
    public Label lblTime;
    public Label lblDate;
    public Label lblAccountName;
    public JFXButton btnHome;

    public void initialize() throws IOException {

        setUi("Home");

        lblAccountName.setText(userName);

        new LoadDateAndTime().loadDateAndTime(lblDate, lblTime);
    }

    public void btnHomeFormOnAction(ActionEvent actionEvent) throws IOException {
        setUi("Home");
    }

    public void btnCustomerFormOnAction(ActionEvent actionEvent) throws IOException {
        setUi("Customer");
    }

    public void btnSupplierFormOnAction(ActionEvent actionEvent) throws IOException {
        setUi("Supplier");
    }

    public void btnOrderFormOnAction(ActionEvent actionEvent) throws IOException {
        setUi("Orders");
    }

    public void btnSupplierOrdersFormOnAction(ActionEvent actionEvent) throws IOException {
        setUi("SupplierOrders");
    }

    public void btnItemFormOnAction(ActionEvent actionEvent) throws IOException {
        setUi("Item");
    }

    public void btnPlaceOrderFormOnAction(ActionEvent actionEvent) throws IOException {
        setUi("PlaceOrder");
    }

    public void btnSupplyOrderFormOnAction(ActionEvent actionEvent) throws IOException {
        setUi("SupplyOrder");
    }

    public void btnReturnFormOnAction(ActionEvent actionEvent) throws IOException {
        setUi("Return");
    }

    public void btnRentFormOnAction(ActionEvent actionEvent) throws IOException {
        setUi("PlaceRent");
    }

    public void btnRentDetailsFormOnAction(ActionEvent actionEvent) throws IOException {
        setUi("RentDetails");
    }

    public void btnReturnDetailsFormOnAction(ActionEvent actionEvent) throws IOException {
        setUi("ReturnDetails");
    }

    private void setUi(String URI) throws IOException {
        dashBoardContext.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("../view/" + URI + "Form.fxml"));
        dashBoardContext.getChildren().add(parent);

    }


}
