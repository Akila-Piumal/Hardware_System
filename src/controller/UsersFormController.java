package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.User;
import util.CrudUtil;
import view.TM.UsersTM;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsersFormController {

    public TableView<UsersTM> tblUsers;
    public AnchorPane dashBoardContext;

    public void initialize() {
        tblUsers.getColumns().get(0).setCellValueFactory(new PropertyValueFactory("id"));
        tblUsers.getColumns().get(1).setCellValueFactory(new PropertyValueFactory("userName"));
        tblUsers.getColumns().get(2).setCellValueFactory(new PropertyValueFactory("password"));
        tblUsers.getColumns().get(3).setCellValueFactory(new PropertyValueFactory("role"));
        tblUsers.getColumns().get(4).setCellValueFactory(new PropertyValueFactory("btnDetails"));

        loadAllUsers();
    }

    private void loadAllUsers() {
        try {
            ResultSet resultSet = CrudUtil.executeQuery("SELECT * FROM user u INNER JOIN log l on u.id = l.id");
//            ArrayList<User> users=new ArrayList<>();
            ObservableList<UsersTM> tableList = FXCollections.observableArrayList();
            while (resultSet.next()) {
                Button btnDetails = new Button("Details");
                User user = new User(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getDouble(6), resultSet.getString(7));
//                users.add(user);
                tableList.add(new UsersTM(user.getId(), resultSet.getString(8), resultSet.getString(9), user.getRole(), btnDetails));

                btnDetails.setStyle("-fx-background-color: #00b894");
                btnDetails.setOnAction(event -> {
                    UsersDetailsFormController.user = user;

                    try {
                        setUi("UsersDetails");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                });
            }
            tblUsers.setItems(tableList);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void btnHomeOnAction(ActionEvent actionEvent) throws IOException {
        dashBoardContext.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("../view/HomeForm.fxml"));
        dashBoardContext.getChildren().add(parent);
    }

    public void btnAddUserOnAction(ActionEvent actionEvent) throws IOException {
        setUi("AddUser");
    }

    public void btnUpdateUserOnAction(ActionEvent actionEvent) throws IOException {
        setUi("UpdateUser");
    }

    public void btnRemoveUserOnAction(ActionEvent actionEvent) throws IOException {
        setUi("RemoveUser");
    }

    private void setUi(String URI) throws IOException {

        Parent parent = FXMLLoader.load(getClass().getResource("../view/" + URI + "Form.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle(URI);
        stage.show();
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                try {
                    Parent parent = FXMLLoader.load(getClass().getResource("../view/UsersForm.fxml"));
                    dashBoardContext.getChildren().clear();
                    dashBoardContext.getChildren().add(parent);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
