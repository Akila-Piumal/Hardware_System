package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.Item;
import util.CrudUtil;
import util.Validate;
import view.TM.CartTMSupply;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class NewItemFormController {

    public AnchorPane dashBoardContext;
    public JFXTextField txtItemCode;
    public JFXTextField txtDescription;
    public JFXTextField txtBuyingPrice;
    public JFXTextField txtSellingPrice;
    public JFXTextField txtQty;
    public JFXButton btnAdd;
    public ComboBox<String> cmbStatus;
    LinkedHashMap<JFXTextField, Pattern> map = new LinkedHashMap<>();

    public void initialize() {

        // set Combo box values
        cmbStatus.getItems().add("For Sale");
        cmbStatus.getItems().add("For Rent");

        cmbStatus.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue==null) {
                btnAdd.setDisable(true);
            }else{
                btnAdd.setDisable(false);
            }
        });

        // Set new Item ID
        setItemCode();
        txtItemCode.setEditable(false);

        //Patterns for textFields
        Pattern descriptionPattern = Pattern.compile("^[A-z/ 0-9-\u0D80-\u0DFF]{3,20}$");
        Pattern sellingPricePattern = Pattern.compile("^[0-9][0-9]{0,}(.[0-9]{2})?$");
        Pattern buyingPricePattern = Pattern.compile("^[0-9][0-9]{0,}(.[0-9]{2})?$");
        Pattern qtyPattern = Pattern.compile("^[1-9]([0-9]{1,})?$");



        // input textField and Pattern to map
        map.put(txtDescription,descriptionPattern);
        map.put(txtBuyingPrice,buyingPricePattern);
        map.put(txtSellingPrice,sellingPricePattern);
        map.put(txtQty,qtyPattern);

        if (cmbStatus.getValue()==null){
            btnAdd.setDisable(true);
        }

    }

    private void setItemCode() {
        try {
            String lastItemCode = ItemCrudController.getLastItemCode();
            String subString = lastItemCode.substring(1);
            int i = Integer.parseInt(subString) + 1;
            String s = String.valueOf(i);

            if (s.length() == 1) {
                txtItemCode.setText("P00" + s);
            } else if (s.length() == 2) {
                txtItemCode.setText("P0" + s);
            } else {
                txtItemCode.setText("P" + s);
            }

            txtItemCode.setEditable(false);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void btnSaveItemOnAction(ActionEvent actionEvent) {
        saveItem();
    }

    private void saveItem() {
        Item item = new Item(txtItemCode.getText(), txtDescription.getText(), Double.parseDouble(txtBuyingPrice.getText()), Double.parseDouble(txtSellingPrice.getText()), Integer.parseInt(txtQty.getText()), cmbStatus.getValue());


        try {
            if (ItemCrudController.saveItem(item)) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Saved");
                alert.showAndWait();

                double buyingPrice = item.getBuyingPrice();
                int qty = item.getQtyOnHand();
                double totalCost = buyingPrice * qty;

                Button btn = new Button("Delete");

                CartTMSupply tm = new CartTMSupply(item.getCode(), item.getDescription(), buyingPrice, item.getSellingPrice(), qty, totalCost, btn);
                SupplyOrderFormController.tableList.add(tm);

                btn.setOnAction(event -> {
                    SupplyOrderFormController.tableList.remove(tm);
                    try {
                        CrudUtil.executeUpdate("DELETE FROM item WHERE code=?", tm.getCode());
                        Stage stage = (Stage) dashBoardContext.getScene().getWindow();
                        stage.getOnCloseRequest().handle(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                });

                Stage stage = (Stage) dashBoardContext.getScene().getWindow();
                stage.getOnCloseRequest().handle(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
                stage.close();
            } else {
                new Alert(Alert.AlertType.WARNING, "Something went Wrong").show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void textFields_Key_Released(KeyEvent keyEvent) {

        // Validate the textFields
        Object response = new Validate().validate(map, btnAdd);

        if (response instanceof Boolean){
            btnAdd.setDisable(true);
        }

        //Enter key pressed
        if (keyEvent.getCode()== KeyCode.ENTER) {
            if(response instanceof JFXTextField){
                JFXTextField textField= (JFXTextField) response;
                textField.requestFocus();
            }else if (response instanceof Boolean){
                // if all details are correct
                if (cmbStatus.getValue()!=null){
                    saveItem();
                }
            }
        }


    }
}
