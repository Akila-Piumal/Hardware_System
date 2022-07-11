package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import util.CrudUtil;
import view.TM.SupplierOrderTM;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SupplierOrdersFormController {
    public TableView<SupplierOrderTM> tblSupplierOrders;
    ObservableList<SupplierOrderTM> obList= FXCollections.observableArrayList();

    public void initialize(){

        tblSupplierOrders.getColumns().get(0).setCellValueFactory(new PropertyValueFactory("id"));
        tblSupplierOrders.getColumns().get(1).setCellValueFactory(new PropertyValueFactory("date"));
        tblSupplierOrders.getColumns().get(2).setCellValueFactory(new PropertyValueFactory("time"));
        tblSupplierOrders.getColumns().get(3).setCellValueFactory(new PropertyValueFactory("supplierId"));
        tblSupplierOrders.getColumns().get(4).setCellValueFactory(new PropertyValueFactory("cost"));

        // Load Supplier Orders to the table
        loadAllSupplierOrders();
    }

    // Load Supplier Orders to the table
    private void loadAllSupplierOrders() {
        try {
            ResultSet resultSet = CrudUtil.executeQuery("SELECT * FROM supplierorder");
            while (resultSet.next()){
                SupplierOrderTM tm=new SupplierOrderTM(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),resultSet.getString(5),resultSet.getDouble(4));
                obList.add(tm);
            }
            tblSupplierOrders.setItems(obList);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void btnHomeOnAction(ActionEvent actionEvent) {
    }
}
