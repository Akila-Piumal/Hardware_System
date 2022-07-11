package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.sun.org.apache.xpath.internal.operations.Bool;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import util.CrudUtil;
import util.Validate;
import view.TM.ItemTM;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Optional;
import java.util.regex.Pattern;

public class ItemFormController {
    public JFXTextField txtItemCode;
    public JFXTextField txtDescription;
    public JFXTextField txtBuyingPrice;
    public JFXTextField txtSellingPrice;
    public JFXTextField txtQtyOnHand;
    public TableView<ItemTM> tblItem;
    public ObservableList<ItemTM> obList = FXCollections.observableArrayList();
    public JFXButton btnSave;
    public JFXComboBox<String> cmbStatus;
    public AnchorPane dashBoardContext;
    LinkedHashMap<JFXTextField, Pattern> map = new LinkedHashMap<>();

    public void initialize() {

        tblItem.getColumns().get(0).setCellValueFactory(new PropertyValueFactory("code"));
        tblItem.getColumns().get(1).setCellValueFactory(new PropertyValueFactory("description"));
        tblItem.getColumns().get(2).setCellValueFactory(new PropertyValueFactory("buyingPrice"));
        tblItem.getColumns().get(3).setCellValueFactory(new PropertyValueFactory("sellingPrice"));
        tblItem.getColumns().get(4).setCellValueFactory(new PropertyValueFactory("qtyOnHand"));
        tblItem.getColumns().get(5).setCellValueFactory(new PropertyValueFactory("status"));
        tblItem.getColumns().get(6).setCellValueFactory(new PropertyValueFactory("box"));

        // set Combo box values
        cmbStatus.getItems().add("For Sale");
        cmbStatus.getItems().add("For Rent");

        cmbStatus.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue==null) {
                btnSave.setDisable(true);
            }else{
                btnSave.setDisable(false);
            }
        });

        // Load all items to the table
        loadAllItems();

        //set new Item code
        setNewItemCode();
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
        map.put(txtQtyOnHand,qtyPattern);

        if (cmbStatus.getValue()==null){
            btnSave.setDisable(true);
        }

    }

    //set new Item code
    private void setNewItemCode() {


        try {
            String lastItemCode = ItemCrudController.getLastItemCode();
            String substring = lastItemCode.substring(1);
            int i = Integer.parseInt(substring) + 1;
            String s = String.valueOf(i);

            if (s.length() == 1) {
                txtItemCode.setText("P00" + s);
            } else if (s.length() == 2) {
                txtItemCode.setText("P0" + s);
            } else {
                txtItemCode.setText("P" + s);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Load All items to the table
    private void loadAllItems() {
        try {
            ResultSet resultSet = CrudUtil.executeQuery("SELECT * FROM item");

            while (resultSet.next()) {
                // Add update Button
                JFXButton updateBtn = new JFXButton("update");
                updateBtn.setStyle("-fx-background-color: #55efc4");

                // Add delete Button
                JFXButton deleteBtn = new JFXButton("delete");
                deleteBtn.setStyle("-fx-background-color: red");

                // Set the buttons to HBox
                HBox box = new HBox(10, updateBtn, deleteBtn);


                ItemTM tm = new ItemTM(resultSet.getString(1), resultSet.getString(2), resultSet.getDouble(3), resultSet.getDouble(4), resultSet.getInt(5), resultSet.getString(6), box);

                setAction(updateBtn, deleteBtn, tm);

                obList.add(tm);


            }

            tblItem.setItems(obList);

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Set Action to the update And delete buttons
    private void setAction(JFXButton updateBtn, JFXButton deleteBtn, ItemTM tm) {
        // Set Action to update Button
        updateBtn.setOnAction(event -> {

            // Select the Current Row
            tblItem.getSelectionModel().select(tm);

            // Set Current details of customer to the textFields
            txtItemCode.setText(tm.getCode());
            txtItemCode.setEditable(false);
            txtDescription.setText(tm.getDescription());
            txtBuyingPrice.setText(String.valueOf(tm.getBuyingPrice()));
            txtSellingPrice.setText(String.valueOf(tm.getSellingPrice()));
            txtQtyOnHand.setText(String.valueOf(tm.getQtyOnHand()));
            cmbStatus.getSelectionModel().select(tm.getStatus());

            // Change the save button to Update button
            btnSave.setText("Update");
            btnSave.setOnAction(event1 -> {
                ItemTM tm2 = new ItemTM(txtItemCode.getText(), txtDescription.getText(), Double.parseDouble(txtBuyingPrice.getText()), Double.parseDouble(txtSellingPrice.getText()), Integer.parseInt(txtQtyOnHand.getText()), cmbStatus.getValue(), tm.getBox());

                try {
                    if (CrudUtil.executeUpdate("UPDATE item SET description=?,buyingPrice=?,sellingPrice=?,qtyOnHand=?,status=? WHERE code=?", tm2.getDescription(), tm2.getBuyingPrice(), tm2.getSellingPrice(), tm2.getQtyOnHand(), tm2.getStatus(), tm2.getCode())) {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Updated");
                        alert.showAndWait();

                        Parent parent = FXMLLoader.load(getClass().getResource("../view/ItemForm.fxml"));
                        dashBoardContext.getChildren().clear();
                        dashBoardContext.getChildren().add(parent);
                    }
                } catch (SQLException | ClassNotFoundException | IOException e) {
                    e.printStackTrace();
                }
            });
        });

        // Set Action to Delete Button
        deleteBtn.setOnAction(event -> {
            try {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are You Sure ? ", ButtonType.YES, ButtonType.NO);
                Optional<ButtonType> buttonType = alert.showAndWait();
                if (buttonType.get().equals(ButtonType.YES)) {
                    CrudUtil.executeUpdate("DELETE FROM item WHERE code=?", tm.getCode());
                    tblItem.getSelectionModel().clearSelection();
                    obList.remove(tm);
                    tblItem.refresh();
                    setNewItemCode();
                }

            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
    }

    // Save Item
    public void btnSaveOnAction(ActionEvent actionEvent) {
        saveItem();
    }

    private void saveItem() {
        // Add update Button
        JFXButton updateBtn = new JFXButton("update");
        updateBtn.setStyle("-fx-background-color: #55efc4");

        // Add delete Button
        JFXButton deleteBtn = new JFXButton("delete");
        deleteBtn.setStyle("-fx-background-color: red");

        // Set the buttons to HBox
        HBox box = new HBox(10, updateBtn, deleteBtn);

        ItemTM tm = new ItemTM(txtItemCode.getText(), txtDescription.getText(), Double.parseDouble(txtBuyingPrice.getText()), Double.parseDouble(txtSellingPrice.getText()), Integer.parseInt(txtQtyOnHand.getText()), cmbStatus.getValue(), box);

        // Set Action to the buttons
        setAction(updateBtn, deleteBtn, tm);

        try {
            if (CrudUtil.executeUpdate("INSERT INTO item VALUES (?,?,?,?,?,?)", tm.getCode(),tm.getDescription(),tm.getBuyingPrice(),tm.getSellingPrice(),tm.getQtyOnHand(),tm.getStatus())) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Saved.");
                alert.showAndWait();
                obList.add(tm);
                tblItem.refresh();
                txtItemCode.clear();
                txtBuyingPrice.clear();
                txtSellingPrice.clear();
                txtDescription.clear();
                txtQtyOnHand.clear();
                cmbStatus.getSelectionModel().clearSelection();
                setNewItemCode();
            }else{
                new Alert(Alert.AlertType.WARNING,"Something went Wrong").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // back to home
    public void btnHomeOnAction(ActionEvent actionEvent) throws IOException {
        dashBoardContext.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("../view/HomeForm.fxml"));
        dashBoardContext.getChildren().add(parent);
    }

    public void textFields_Key_Released(KeyEvent keyEvent) {

        // Validate the textFields
        Object response = new Validate().validate(map, btnSave);

        if (response instanceof Boolean){
            btnSave.setDisable(true);
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
