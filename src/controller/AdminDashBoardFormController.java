package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import util.LoadDateAndTime;

import java.io.IOException;

public class AdminDashBoardFormController {
    public static String userName;
    public Label lblAccountName;
    public Label lblDate;
    public Label lblTime;
    public AnchorPane dashBoardContext;

    public void initialize() throws IOException {
        setUi("Home");

        lblAccountName.setText(userName);

        new LoadDateAndTime().loadDateAndTime(lblDate, lblTime);
    }

    public void btnHomeFormOnAction(ActionEvent actionEvent) throws IOException {
        setUi("Home");
    }

    public void btnUsersFormOnAction(ActionEvent actionEvent) throws IOException {
        setUi("Users");
    }

    public void btnEmployeeFormOnAction(ActionEvent actionEvent) throws IOException {
        setUi("Employee");
    }

    public void btnCustomerFormOnAction(ActionEvent actionEvent) throws IOException {
        setUi("Customer");
    }

    public void btnSupplierFormOnAction(ActionEvent actionEvent) throws IOException {
        setUi("Supplier");
    }

    public void btnItemFormOnAction(ActionEvent actionEvent) throws IOException {
        setUi("Item");
    }

    public void btnOrderFormOnAction(ActionEvent actionEvent) throws IOException {
        setUi("Orders");
    }

    public void btnSupplierOrdersFormOnAction(ActionEvent actionEvent) throws IOException {
        setUi("SupplierOrders");
    }

    public void btnRentDetailsFormOnAction(ActionEvent actionEvent) throws IOException {
        setUi("RentDetails");
    }

    public void btnPlaceOrderFormOnAction(ActionEvent actionEvent) throws IOException {
        setUi("PlaceOrder");
    }

    public void btnSupplyOrderFormOnAction(ActionEvent actionEvent) throws IOException {
        setUi("SupplyOrder");
    }

    public void btnRentFormOnAction(ActionEvent actionEvent) throws IOException {
        setUi("PlaceRent");
    }

    public void btnReturnFormOnAction(ActionEvent actionEvent) throws IOException {
        setUi("Return");
    }

    public void btnReturnDetailsFormOnAction(ActionEvent actionEvent) throws IOException {
        setUi("ReturnDetails");
    }

    public void btnIncomeFormOnAction(ActionEvent actionEvent) throws IOException {
        setUi("Income");
    }

    private void setUi(String URI) throws IOException {
        dashBoardContext.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("../view/" + URI + "Form.fxml"));
        dashBoardContext.getChildren().add(parent);

    }


}
