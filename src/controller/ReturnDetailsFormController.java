package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import util.CrudUtil;
import view.TM.ReturnDetailsTM;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReturnDetailsFormController {
    public TableView<ReturnDetailsTM> tblReturns;
    public AnchorPane dashBoardContext;

    public void initialize() {

        tblReturns.getColumns().get(0).setCellValueFactory(new PropertyValueFactory("ReturnId"));
        tblReturns.getColumns().get(1).setCellValueFactory(new PropertyValueFactory("date"));
        tblReturns.getColumns().get(2).setCellValueFactory(new PropertyValueFactory("cost"));
        tblReturns.getColumns().get(3).setCellValueFactory(new PropertyValueFactory("btnDetails"));

        try {
            loadReturnDetails();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void loadReturnDetails() throws SQLException, ClassNotFoundException {

        ObservableList<ReturnDetailsTM> tableList = FXCollections.observableArrayList();

        ResultSet resultSet = CrudUtil.executeQuery("SELECT * FROM returnorder");

        while (resultSet.next()) {
            Button btn = new Button("Details");
            btn.setStyle("-fx-background-color: #00b894");

            tableList.add(new ReturnDetailsTM(resultSet.getString(1), resultSet.getString(2), resultSet.getDouble(3), btn));

            String rentId = resultSet.getString(1);

            btn.setOnAction(event -> {
                try {
                    ReturnItemDetailsFormController.returnId = rentId;

                    Parent parent = FXMLLoader.load(getClass().getResource("../view/ReturnItemDetailsForm.fxml"));
                    Scene scene = new Scene(parent);
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.setTitle("Return Item details");
                    stage.show();

                } catch (IOException e) {
                    System.out.println(e);
                }

            });
        }

        tblReturns.setItems(tableList);

    }

    public void btnHomeOnAction(ActionEvent actionEvent) throws IOException {
        dashBoardContext.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("../view/HomeForm.fxml"));
        dashBoardContext.getChildren().add(parent);
    }
}
