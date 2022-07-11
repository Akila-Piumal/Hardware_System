package controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.Log;
import model.User;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class RemoveUserFormController {
    public JFXComboBox<String> cmbUserId;
    public JFXTextField txtName;
    public JFXTextField txtRole;
    public JFXTextField txtPassword;
    public JFXTextField txtUserName;
    public AnchorPane dashBoardContext;

    public void initialize(){

        // Load User ids to combo box
        loadUserIds();

        cmbUserId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                User user = UserCrudController.getUser(newValue);
                txtName.setText(user.getName());
                txtRole.setText(user.getRole());
                Log loggingDetails = LogCrudController.getLoggingDetails(newValue);
                txtUserName.setText(loggingDetails.getUserName());
                txtPassword.setText(loggingDetails.getPassword());

            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
    }

    // Load User ids to combo box
    private void loadUserIds() {
        ObservableList<String> userIdList= FXCollections.observableArrayList();
        try {
            ResultSet resultSet = CrudUtil.executeQuery("SELECT u.id FROM user u INNER JOIN log l on u.id = l.id;");
            while (resultSet.next()){
                userIdList.add(resultSet.getString(1));
            }

            cmbUserId.setItems(userIdList);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void btnRemoveUserOnAction(ActionEvent actionEvent) {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are You Sure ? ", ButtonType.YES, ButtonType.NO);
            Optional<ButtonType> buttonType = alert.showAndWait();
            if (buttonType.get().equals(ButtonType.YES)) {
                if (CrudUtil.executeUpdate("DELETE FROM log WHERE id=?",cmbUserId.getValue())) {
                    Stage stage = (Stage) dashBoardContext.getScene().getWindow();
                    stage.getOnCloseRequest().handle(new WindowEvent(stage,WindowEvent.WINDOW_CLOSE_REQUEST));
                    stage.close();
                }
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
