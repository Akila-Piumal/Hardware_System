package controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.User;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AddUserFormController {
    public JFXComboBox<String> cmbUserId;
    public JFXTextField txtName;
    public JFXTextField txtRole;
    public JFXTextField txtUserName;
    public JFXTextField txtPassword;
    public AnchorPane dashBoardContext;

    public void initialize() {

        // Load User ids to combo box
        loadUserIds();

        cmbUserId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                User user = UserCrudController.getUser(newValue);
                txtName.setText(user.getName());
                txtRole.setText(user.getRole());
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
    }

    // Load User ids to combo box
    private void loadUserIds() {
        ObservableList<String> idList = FXCollections.observableArrayList();
        try {
            ResultSet resultSet = CrudUtil.executeQuery("SELECT u.id FROM user u LEFT JOIN log l on u.id = l.id WHERE l.id IS NULL;");
            while (resultSet.next()) {
                idList.add(resultSet.getString(1));
            }

            cmbUserId.setItems(idList);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Add New System user
    public void btnAddUserOnAction(ActionEvent actionEvent) {
        try {
            if (CrudUtil.executeUpdate("INSERT INTO log VALUES (?,?,?)", txtUserName.getText(), txtPassword.getText(), cmbUserId.getValue())) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Success");
                alert.showAndWait();
                Stage stage = (Stage) dashBoardContext.getScene().getWindow();
                stage.getOnCloseRequest().handle(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
                stage.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
