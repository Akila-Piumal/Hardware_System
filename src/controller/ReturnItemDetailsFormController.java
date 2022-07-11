package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import view.TM.ReturnItemDetailsTM;

import java.sql.SQLException;
import java.util.ArrayList;

public class ReturnItemDetailsFormController {
    public static String returnId;
    public TableView<ReturnItemDetailsTM> tblReturnItemDetails;

    public void initialize() {

        tblReturnItemDetails.getColumns().get(0).setCellValueFactory(new PropertyValueFactory("itemCode"));
        tblReturnItemDetails.getColumns().get(1).setCellValueFactory(new PropertyValueFactory("description"));
        tblReturnItemDetails.getColumns().get(2).setCellValueFactory(new PropertyValueFactory("unitPrice"));
        tblReturnItemDetails.getColumns().get(3).setCellValueFactory(new PropertyValueFactory("qty"));
        tblReturnItemDetails.getColumns().get(4).setCellValueFactory(new PropertyValueFactory("price"));

        loadAllItemDetails();
    }

    private void loadAllItemDetails() {
        try {
            ArrayList<ReturnItemDetailsTM> returnItemDetails = new ReturnCrudController().getReturnItemDetails(returnId);
            ObservableList<ReturnItemDetailsTM> tableList = FXCollections.observableArrayList();

            for (ReturnItemDetailsTM tm : returnItemDetails) {
                tableList.add(tm);
            }
            tblReturnItemDetails.setItems(tableList);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
