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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import util.CrudUtil;
import util.Validate;
import view.TM.SupplierTM;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Optional;
import java.util.regex.Pattern;

public class SupplierFormController {
    public AnchorPane dashBoardContext;
    public TableView<SupplierTM> tblSupplier;
    public JFXTextField txtSupplierId;
    public JFXTextField txtSupplierName;
    public JFXTextField txtSupplierAddress;
    public JFXTextField txtContact;
    public JFXTextField txtCompany;
    public JFXButton btnSave;

    ObservableList<SupplierTM> obList = FXCollections.observableArrayList();
    LinkedHashMap<JFXTextField, Pattern> map = new LinkedHashMap<>();

    public void initialize() {

        // Set Table Data to the columns

        tblSupplier.getColumns().get(0).setCellValueFactory(new PropertyValueFactory("id"));
        tblSupplier.getColumns().get(1).setCellValueFactory(new PropertyValueFactory("name"));
        tblSupplier.getColumns().get(2).setCellValueFactory(new PropertyValueFactory("address"));
        tblSupplier.getColumns().get(3).setCellValueFactory(new PropertyValueFactory("contact"));
        tblSupplier.getColumns().get(4).setCellValueFactory(new PropertyValueFactory("company"));
        tblSupplier.getColumns().get(5).setCellValueFactory(new PropertyValueFactory("box"));

        // Load Supplier Details to the Table
        loadSuppliers();

        //set new Supplier id
        setNewSupplierId();
        txtSupplierId.setEditable(false);

        //Patterns for textFields
        Pattern namePattern = Pattern.compile("^[A-z ]{3,15}$");
        Pattern addressPattern = Pattern.compile("^[A-z/,0-9 ]{4,30}$");
        Pattern contactPattern=Pattern.compile("^(07)(7|8|6|5|4|1|0|2)[0-9]{7}$");
        Pattern companyPattern = Pattern.compile("^[A-z0-9]{3,15}$");

        // input textField and Pattern to map
        map.put(txtSupplierName,namePattern);
        map.put(txtSupplierAddress,addressPattern);
        map.put(txtContact,contactPattern);
        map.put(txtCompany,companyPattern);
    }

    //set new Supplier id
    private void setNewSupplierId() {
        String lastSupplierId = null;
        try {
            lastSupplierId = SupplierCrudController.getLastSupplierId();
            String substring = lastSupplierId.substring(1);
            int i = Integer.parseInt(substring) + 1;
            String s = String.valueOf(i);

            if (s.length() == 1) {
                txtSupplierId.setText("S00" + s);
            } else if (s.length() == 2) {
                txtSupplierId.setText("S0" + s);
            } else {
                txtSupplierId.setText("S" + s);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    // Load Supplier Details to The Table
    private void loadSuppliers() {
        try {
            ResultSet resultSet = CrudUtil.executeQuery("SELECT * FROM supplier");

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
                SupplierTM tm = new SupplierTM(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), box);

                // Set Action to Buttons
                setAction(updateBtn, deleteBtn, tm);

                // add tm object to observable list
                obList.add(tm);
            }

            // insert data in to the table
            tblSupplier.setItems(obList);

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // set Action to update And delete Button
    private void setAction(JFXButton updateBtn, JFXButton deleteBtn, SupplierTM tm) {

        // Set Action to update Button
        updateBtn.setOnAction(event -> {

            // Select the Current Row
            tblSupplier.getSelectionModel().select(tm);

            // Set Current details of customer to the textFields
            txtSupplierId.setText(tm.getId());
            txtSupplierId.setEditable(false);
            txtSupplierName.setText(tm.getName());
            txtSupplierAddress.setText(tm.getAddress());
            txtContact.setText(tm.getContact());
            txtCompany.setText(tm.getCompany());

            // Change the save button to Update button
            btnSave.setText("Update");
            btnSave.setOnAction(event1 -> {
                SupplierTM tm2 = new SupplierTM(txtSupplierId.getText(), txtSupplierName.getText(), txtSupplierAddress.getText(), txtContact.getText(), txtCompany.getText(), tm.getBox());

                try {
                    if (CrudUtil.executeUpdate("UPDATE supplier SET name=?,address=?,contact=?,company=? WHERE id=?", tm2.getName(), tm2.getAddress(), tm2.getContact(), tm2.getCompany(), tm2.getId())) {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Updated");
                        alert.showAndWait();

                        Parent parent = FXMLLoader.load(getClass().getResource("../view/SupplierForm.fxml"));
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
                    CrudUtil.executeUpdate("DELETE FROM supplier WHERE id=?", tm.getId());
                    tblSupplier.getSelectionModel().clearSelection();
                    obList.remove(tm);
                    tblSupplier.refresh();
                    setNewSupplierId();
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

    // Save Supplier on action
    public void btnSaveOnAction(ActionEvent actionEvent) {
        saveSupplier();
    }

    // Save the Supplier
    private void saveSupplier() {
        JFXButton updateBtn = new JFXButton("update");
        updateBtn.setStyle("-fx-background-color: #55efc4");

        JFXButton deleteBtn = new JFXButton("delete");
        deleteBtn.setStyle("-fx-background-color: red");

        HBox box = new HBox(10, updateBtn, deleteBtn);

        SupplierTM tm = new SupplierTM(txtSupplierId.getText(), txtSupplierName.getText(), txtSupplierAddress.getText(), txtContact.getText(), txtCompany.getText(), box);

        // Set Action to the buttons
        setAction(updateBtn, deleteBtn, tm);

        try {
            if (CrudUtil.executeUpdate("INSERT INTO supplier VALUES (?,?,?,?,?)", tm.getId(), tm.getName(), tm.getAddress(), tm.getContact(), tm.getCompany())) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Saved");
                alert.showAndWait();

                Parent parent = FXMLLoader.load(getClass().getResource("../view/SupplierForm.fxml"));
                dashBoardContext.getChildren().clear();
                dashBoardContext.getChildren().add(parent);
            } else {
                new Alert(Alert.AlertType.WARNING, "Something went Wrong").show();
            }
        } catch (SQLException | ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }

    public void textFields_Key_Released(KeyEvent keyEvent) {

        // Validate the textFields
        Object response = new Validate().validate(map, btnSave);

        // if Enter key pressed
        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof JFXTextField) {
                JFXTextField textField = (JFXTextField) response;
                textField.requestFocus();
            } else if (response instanceof Boolean) {
                // if all details are correct
                saveSupplier();
            }
        }

    }
}
