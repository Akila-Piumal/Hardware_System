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
import view.TM.EmployeeTM;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Optional;
import java.util.regex.Pattern;

public class EmployeeFormController {
    public JFXTextField txtId;
    public JFXTextField txtName;
    public JFXTextField txtAddress;
    public JFXTextField txtContact;
    public JFXTextField txtNic;
    public JFXTextField txtSalary;
    public JFXButton btnUpdate;
    public TableView<EmployeeTM> tblEmployee;
    public AnchorPane dashBoardContext;
    ObservableList<EmployeeTM> obList = FXCollections.observableArrayList();
    LinkedHashMap<JFXTextField, Pattern> map = new LinkedHashMap<>();

    public void initialize() {

        tblEmployee.getColumns().get(0).setCellValueFactory(new PropertyValueFactory("id"));
        tblEmployee.getColumns().get(1).setCellValueFactory(new PropertyValueFactory("nic"));
        tblEmployee.getColumns().get(2).setCellValueFactory(new PropertyValueFactory("name"));
        tblEmployee.getColumns().get(3).setCellValueFactory(new PropertyValueFactory("address"));
        tblEmployee.getColumns().get(4).setCellValueFactory(new PropertyValueFactory("contact"));
        tblEmployee.getColumns().get(5).setCellValueFactory(new PropertyValueFactory("salary"));
        tblEmployee.getColumns().get(6).setCellValueFactory(new PropertyValueFactory("box"));

        try {

            // Load All Employees
            loadEmployees();

            //set new employee id
            setNewEmployeeId();
            txtId.setEditable(false);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        //Patterns for textFields
        Pattern nicPattern = Pattern.compile("^[0-9]{9}(V|[0-9]{3})$");
        Pattern namePattern = Pattern.compile("^[A-z ]{3,15}$");
        Pattern addressPattern = Pattern.compile("^[A-z/,0-9 ]{4,30}$");
        Pattern contactPattern=Pattern.compile("^(07)(7|8|6|5|4|1|0|2)[0-9]{7}$");
        Pattern salaryPattern = Pattern.compile("^[1-9][0-9]{3,}(.[0-9]{2})?$");

        // input textField and Pattern to map
        map.put(txtNic,nicPattern);
        map.put(txtName,namePattern);
        map.put(txtAddress,addressPattern);
        map.put(txtContact,contactPattern);
        map.put(txtSalary,salaryPattern);

    }

    private void setNewEmployeeId() throws SQLException, ClassNotFoundException {
        String lastEmployeeId = new EmployeeCrudController().getLastEmployeeId();
        String substring = lastEmployeeId.substring(1);
        int i = Integer.parseInt(substring) + 1;
        String s = String.valueOf(i);

        if (s.length() == 1) {
            txtId.setText("U00" + s);
        } else if (s.length() == 2) {
            txtId.setText("U0" + s);
        } else {
            txtId.setText("U" + s);
        }
    }

    // Load All employees to the table
    private void loadEmployees() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.executeQuery("SELECT * FROM user WHERE role=?", "employee");

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
            EmployeeTM tm = new EmployeeTM(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getDouble(6), box);

            // Set Action to Buttons
            setAction(updateBtn, deleteBtn, tm);

            // add tm object to observable list
            obList.add(tm);

        }

        // insert data in to the table
        tblEmployee.setItems(obList);
    }

    // Set Actions to the Buttons
    private void setAction(JFXButton updateBtn, JFXButton deleteBtn, EmployeeTM tm) {

        // Set Action to update Button
        updateBtn.setOnAction(event -> {

            // Set Current details of Employee to the textFields
            txtId.setText(tm.getId());
            txtName.setText(tm.getName());
            txtNic.setText(tm.getNic());
            txtAddress.setText(tm.getAddress());
            txtContact.setText(tm.getContact());
            txtSalary.setText(String.valueOf(tm.getSalary()));

            // Change the save button to Update button
            btnUpdate.setText("Update");
            txtId.setEditable(false);
            btnUpdate.setOnAction(event1 -> {
                EmployeeTM tm2 = new EmployeeTM(txtId.getText(), txtNic.getText(), txtName.getText(), txtAddress.getText(), txtContact.getText(), Double.parseDouble(txtSalary.getText()), tm.getBox());

                try {
                    if (CrudUtil.executeUpdate("UPDATE user SET nic=?,name=?,address=?,contact=?,salary=?,role=? WHERE id=?", tm2.getNic(), tm2.getName(), tm2.getAddress(), tm2.getContact(), tm2.getSalary(), "employee", tm2.getId())) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION, "updated");
                        alert.showAndWait();

                        Parent parent = FXMLLoader.load(getClass().getResource("../view/EmployeeForm.fxml"));
                        dashBoardContext.getChildren().clear();
                        dashBoardContext.getChildren().add(parent);
                    } else {
                        new Alert(Alert.AlertType.ERROR, "Error").show();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
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
                    CrudUtil.executeUpdate("DELETE FROM user WHERE id=?", tm.getId());
                    obList.remove(tm);
                    setNewEmployeeId();
                }

            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

    }

    // Back to Home
    public void btnHomeOnAction(ActionEvent actionEvent) throws IOException {
        dashBoardContext.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("../view/HomeForm.fxml"));
        dashBoardContext.getChildren().add(parent);
    }

    // Save Employee On Action
    public void btnSaveOnAction(ActionEvent actionEvent) {
        saveEmployee();
    }

    //Save the Employee
    private void saveEmployee() {
        JFXButton updateBtn = new JFXButton("update");
        updateBtn.setStyle("-fx-background-color: #55efc4");

        JFXButton deleteBtn = new JFXButton("delete");
        deleteBtn.setStyle("-fx-background-color: red");

        HBox box = new HBox(10, updateBtn, deleteBtn);

        EmployeeTM tm = new EmployeeTM(txtId.getText(), txtNic.getText(), txtName.getText(), txtAddress.getText(), txtContact.getText(), Double.parseDouble(txtSalary.getText()), box);

        // Set Action to the buttons
        setAction(updateBtn, deleteBtn, tm);

        try {
            if (CrudUtil.executeUpdate("INSERT INTO user VALUES (?,?,?,?,?,?,?)", tm.getId(), tm.getNic(), tm.getName(), tm.getAddress(), tm.getContact(), tm.getSalary(), "employee")) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Saved");
                alert.showAndWait();

                Parent parent = FXMLLoader.load(getClass().getResource("../view/EmployeeForm.fxml"));
                dashBoardContext.getChildren().clear();
                dashBoardContext.getChildren().add(parent);

            } else {
                new Alert(Alert.AlertType.ERROR, "Error").show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void textFields_Key_Released(KeyEvent keyEvent) {

        // Validate the text Fields
        Object response = new Validate().validate(map, btnUpdate);//if response is a textField, content is not matched and response is boolean All details are correct

        //Enter key pressed
        if (keyEvent.getCode()== KeyCode.ENTER){
            if (response instanceof JFXTextField){
                JFXTextField textField= (JFXTextField) response;
                textField.requestFocus();
            }else if (response instanceof Boolean){
                // if all details are correct
                saveEmployee();
            }
        }
    }
}
