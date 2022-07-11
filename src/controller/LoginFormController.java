package controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.User;
import util.CrudUtil;

import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginFormController {
    public JFXTextField txtUserName;
    public JFXPasswordField pwdPassword;
    public CheckBox cbShowPassword;
    public JFXTextField txtPassword;
    public AnchorPane dashBoardContext;

    public void initialize() {
        txtPassword.textProperty().bind(pwdPassword.textProperty());
        txtPassword.visibleProperty().bind(cbShowPassword.selectedProperty());
        pwdPassword.visibleProperty().bind(cbShowPassword.selectedProperty().not());

    }

    public void btnLoginOnAction(ActionEvent actionEvent) throws IOException {
        loginToSystem();

    }

    private void loginToSystem() {

        try {

            // Check the User name and password is valid
            ResultSet resultSet = CrudUtil.executeQuery("SELECT * FROM log WHERE userName=? AND password=?", txtUserName.getText(), pwdPassword.getText());

            // If User Name And Password Are Correct
            if (resultSet.next()) {

                // If the Logging is owner
                if (UserCrudController.getUser(resultSet.getString(3)).getRole().equals("Owner")) {

                    AdminDashBoardFormController.userName = resultSet.getString(1);

                    Stage stage = (Stage) dashBoardContext.getScene().getWindow();
                    stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/AdminDashBoardForm.fxml"))));
                    stage.show();
                } else {

                    // If the Logging is Cashier
                    CashierDashBoardFormController.userName = resultSet.getString(1);

                    Stage stage = (Stage) dashBoardContext.getScene().getWindow();
                    stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/CashierDashBoardForm.fxml"))));
                    stage.show();
                }

            } else {
                // If User Name And Password Are Incorrect
                Alert alert = new Alert(Alert.AlertType.WARNING, "Email Or Password Is Incorrect");
                alert.showAndWait();
                pwdPassword.clear();
            }

        } catch (SQLException | ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }

    public void keyReleased(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (txtUserName.getText() != null) {
                pwdPassword.requestFocus();
                if (pwdPassword.getText().length()>0){
                    loginToSystem();
                }
            }
        }
    }
}
