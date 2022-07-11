package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import util.CrudUtil;
import util.Validate;
import view.TM.CustomerTM;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Optional;
import java.util.regex.Pattern;

public class CustomerFormController {
    public AnchorPane dashBoardContext;
    public JFXTextField txtCustomerId;
    public JFXTextField txtCustomerName;
    public JFXTextField txtCustomerAddress;
    public JFXTextField txtContact;
    public TableView<CustomerTM> tblCustomer;
    public JFXButton btnUpdate;
    ObservableList<CustomerTM> obList = FXCollections.observableArrayList();
    LinkedHashMap<JFXTextField, Pattern> map = new LinkedHashMap<>();

    public void initialize() {

        // Set Table Data to the columns

        tblCustomer.getColumns().get(0).setCellValueFactory(new PropertyValueFactory("id"));
        tblCustomer.getColumns().get(1).setCellValueFactory(new PropertyValueFactory("name"));
        tblCustomer.getColumns().get(2).setCellValueFactory(new PropertyValueFactory("address"));
        tblCustomer.getColumns().get(3).setCellValueFactory(new PropertyValueFactory("contact"));
        tblCustomer.getColumns().get(4).setCellValueFactory(new PropertyValueFactory("box"));

        try {

            // Load customer details to the table
            loadCustomers();

            //set new Customer id
            setNewCustomerId();
            txtCustomerId.setEditable(false);


        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        btnUpdate.setDisable(true);

        //Patterns for textFields
        Pattern namePattern = Pattern.compile("^[A-z ]{3,15}$");
        Pattern addressPattern = Pattern.compile("^[A-z/,0-9 ]{4,30}$");
        Pattern contactPattern = Pattern.compile("^(07)(7|8|6|5|4|1|0|2)[0-9]{7}$");


        // input textField and Pattern to map

        map.put(txtCustomerName, namePattern);
        map.put(txtCustomerAddress, addressPattern);
        map.put(txtContact, contactPattern);
    }

    //set new Customer id
    private void setNewCustomerId() throws SQLException, ClassNotFoundException {
        String lastCustomerId = CustomerCrudController.getLastCustomerId();
        String substring = lastCustomerId.substring(1);
        int i = Integer.parseInt(substring) + 1;
        String s = String.valueOf(i);

        if (s.length() == 1) {
            txtCustomerId.setText("C00" + s);
        } else if (s.length() == 2) {
            txtCustomerId.setText("C0" + s);
        } else {
            txtCustomerId.setText("C" + s);
        }
    }

    // Load customer details to the table
    private void loadCustomers() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.executeQuery("SELECT * FROM customer");

        while (resultSet.next()) {

            // Add update Button
            JFXButton updateBtn = new JFXButton("update");
            updateBtn.setStyle("-fx-background-color: #55efc4");

            // Add delete Button
            JFXButton deleteBtn = new JFXButton("delete");
            deleteBtn.setStyle("-fx-background-color: red");

            // Set the buttons to HBox
            HBox box = new HBox(10, updateBtn, deleteBtn);

            // Create tm to load data to the observable list
            CustomerTM tm = new CustomerTM(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), box);

            // Set Action to Buttons
            setAction(updateBtn, deleteBtn, tm);

            // add tm object to observable list
            obList.add(tm);

        }

        // insert data in to the table
        tblCustomer.setItems(obList);

    }

    // Set Action to Buttons
    private void setAction(JFXButton updateBtn, JFXButton deleteBtn, CustomerTM tm) {

        // Set Action to update Button
        updateBtn.setOnAction(event -> {

            // Select the Current Row
            tblCustomer.getSelectionModel().select(tm);

            // Set Current details of customer to the textFields
            txtCustomerId.setText(tm.getId());
            txtCustomerId.setEditable(false);
            txtCustomerName.setText(tm.getName());
            txtCustomerAddress.setText(tm.getAddress());
            txtContact.setText(tm.getContact());

            // Change the save button to Update button
            btnUpdate.setText("Update");
            btnUpdate.setOnAction(event1 -> {
                CustomerTM tm2 = new CustomerTM(txtCustomerId.getText(), txtCustomerName.getText(), txtCustomerAddress.getText(), txtContact.getText(), tm.getBox());
                try {
                    if (CrudUtil.executeUpdate("UPDATE customer SET name=?,address=?,contact=? WHERE id=?", tm2.getName(), tm2.getAddress(), tm2.getContact(), tm2.getId())) {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Updated");
                        alert.showAndWait();

                        Parent parent = FXMLLoader.load(getClass().getResource("../view/CustomerForm.fxml"));
                        dashBoardContext.getChildren().clear();
                        dashBoardContext.getChildren().add(parent);

                    } else {
                        new Alert(Alert.AlertType.WARNING, "Something went Wrong").show();
                    }
                } catch (SQLException | ClassNotFoundException | IOException e) {
                    e.printStackTrace();
                }
                tblCustomer.refresh();
            });

        });

        // Set Action to Delete Button
        deleteBtn.setOnAction(event -> {
            try {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are You Sure ? ", ButtonType.YES, ButtonType.NO);
                Optional<ButtonType> buttonType = alert.showAndWait();
                if (buttonType.get().equals(ButtonType.YES)) {
                    CrudUtil.executeUpdate("DELETE FROM customer WHERE id=?", tm.getId());
                    obList.remove(tm);
                    setNewCustomerId();
                }

            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

    }

    // Back To the home page
    public void btnHomeOnAction(ActionEvent actionEvent) throws IOException {
        dashBoardContext.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("../view/HomeForm.fxml"));
        dashBoardContext.getChildren().add(parent);
    }

    // Save the Customer
    public void btnSaveOnAction(ActionEvent actionEvent) {
        saveCustomer();
    }

    // Save Customer Details
    private void saveCustomer() {
        JFXButton updateBtn = new JFXButton("update");
        updateBtn.setStyle("-fx-background-color: #55efc4");

        JFXButton deleteBtn = new JFXButton("delete");
        deleteBtn.setStyle("-fx-background-color: red");

        HBox box = new HBox(10, updateBtn, deleteBtn);

        CustomerTM tm = new CustomerTM(txtCustomerId.getText(), txtCustomerName.getText(), txtCustomerAddress.getText(), txtContact.getText(), box);

        // Set Action to the buttons
        setAction(updateBtn, deleteBtn, tm);

        try {

            if (CrudUtil.executeUpdate("INSERT INTO customer VALUES (?,?,?,?)", tm.getId(), tm.getName(), tm.getAddress(), tm.getContact())) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Saved...");
                alert.showAndWait();

                Parent parent = FXMLLoader.load(getClass().getResource("../view/CustomerForm.fxml"));
                dashBoardContext.getChildren().clear();
                dashBoardContext.getChildren().add(parent);

            } else {
                new Alert(Alert.AlertType.WARNING, "Something Went Wrong").show();
            }
        } catch (SQLException | ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }

    }

    // add Event to the textFields
    public void textFields_Key_Released(KeyEvent keyEvent) {

        // validate textFields
        Object response = new Validate().validate(map, btnUpdate);

        //Enter key pressed
        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                // if a textField's date is not valid
                TextField textField = (TextField) response;
                textField.requestFocus();
            } else if (response instanceof Boolean) {
                // if all details are valid
                saveCustomer();
            }
        }
    }
}
