package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import util.CrudUtil;
import view.TM.OrderTM;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrdersFormController {
    public TableView<OrderTM> tblOrders;
    public AnchorPane dashBoardContext;
    ObservableList<OrderTM> obList= FXCollections.observableArrayList();

    public void initialize(){

        tblOrders.getColumns().get(0).setCellValueFactory(new PropertyValueFactory("orderId"));
        tblOrders.getColumns().get(1).setCellValueFactory(new PropertyValueFactory("date"));
        tblOrders.getColumns().get(2).setCellValueFactory(new PropertyValueFactory("time"));
        tblOrders.getColumns().get(3).setCellValueFactory(new PropertyValueFactory("customerId"));
        tblOrders.getColumns().get(4).setCellValueFactory(new PropertyValueFactory("cost"));

        // Load Orders to the table
        loadAllOrders();
    }

    // Load Orders to the table
    private void loadAllOrders() {

        try {
            ResultSet resultSet = CrudUtil.executeQuery("SELECT * FROM `order`");
            while (resultSet.next()){
                
                OrderTM tm=new OrderTM(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),resultSet.getString(5),resultSet.getDouble(4));
                obList.add(tm);
            }

            tblOrders.setItems(obList);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void btnHomeOnAction(ActionEvent actionEvent) throws IOException {
        dashBoardContext.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("../view/HomeForm.fxml"));
        dashBoardContext.getChildren().add(parent);
    }
}
