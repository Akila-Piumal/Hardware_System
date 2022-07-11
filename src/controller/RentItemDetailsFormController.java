package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import util.CrudUtil;
import view.TM.RentItemDetailsTM;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;


public class RentItemDetailsFormController {

    public static String rentId;
    public TableView<RentItemDetailsTM> tblRentItemDetails;
    public AnchorPane dashBoardContext;

    public void initialize() {

        tblRentItemDetails.getColumns().get(0).setCellValueFactory(new PropertyValueFactory("itemCode"));
        tblRentItemDetails.getColumns().get(1).setCellValueFactory(new PropertyValueFactory("description"));
        tblRentItemDetails.getColumns().get(2).setCellValueFactory(new PropertyValueFactory("unitPrice"));
        tblRentItemDetails.getColumns().get(3).setCellValueFactory(new PropertyValueFactory("qty"));
        tblRentItemDetails.getColumns().get(4).setCellValueFactory(new PropertyValueFactory("rentDays"));
        tblRentItemDetails.getColumns().get(5).setCellValueFactory(new PropertyValueFactory("returnDate"));
        tblRentItemDetails.getColumns().get(6).setCellValueFactory(new PropertyValueFactory("cost"));
        tblRentItemDetails.getColumns().get(7).setCellValueFactory(new PropertyValueFactory("box"));

        // Set details to the table
        setAllItemDetails();
    }

    private void setAllItemDetails() {
        try {
            ArrayList<RentItemDetailsTM> rentItemDetails = new RentCrudController().getRentItemDetails(rentId);
            ObservableList<RentItemDetailsTM> tableList = FXCollections.observableArrayList();

            for (RentItemDetailsTM tm : rentItemDetails) {

                // Add action to the remove button

                Button btnRemove=new Button("Remove");
                btnRemove.setStyle("-fx-background-color: red");
                btnRemove.setOnAction(event -> {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure the item was returned ? ", ButtonType.YES, ButtonType.NO);
                    Optional<ButtonType> buttonType = alert.showAndWait();
                    if (buttonType.get().equals(ButtonType.YES)) {

                        // Remove details from table
                        tableList.remove(tm);

                        try {
                            if (CrudUtil.executeUpdate("DELETE FROM `rent detail` WHERE rentId=? AND itemCode=?", rentId, tm.getItemCode())) {
                                CrudUtil.executeUpdate("UPDATE item SET qtyOnHand=qtyOnHand+? WHERE code=?", tm.getQty(), tm.getItemCode());
                                if (tableList.size() == 0) {
                                    CrudUtil.executeUpdate("DELETE FROM rent WHERE id=?", rentId);
                                    Stage stage = (Stage) dashBoardContext.getScene().getWindow();
                                    Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION, "Rent is Closed.");
                                    alert1.showAndWait();
                                    stage.close();
                                    stage.getOnCloseRequest().handle(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
                                } else {
                                    new Alert(Alert.AlertType.CONFIRMATION, "Rent is Closed.").show();
                                }

                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                });

                // Add action to the update button

                Button btnUpdate=new Button("update");
                btnUpdate.setStyle("-fx-background-color: #00b894");
                btnUpdate.setOnAction(event -> {

                    try {
                        UpdateRentFormController.rentId=rentId;
                        UpdateRentFormController.itemCode=tm.getItemCode();
                        Stage stage=new Stage();
                        Parent parent = FXMLLoader.load(getClass().getResource("../view/updateRentForm.fxml"));
                        Scene scene=new Scene(parent);
                        stage.setScene(scene);
                        stage.show();
                        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                            @Override
                            public void handle(WindowEvent event) {
                                try {
                                    Parent parent = FXMLLoader.load(getClass().getResource("../view/RentItemDetailsForm.fxml"));
                                    dashBoardContext.getChildren().clear();
                                    dashBoardContext.getChildren().add(parent);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            }
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                });

                HBox box=new HBox(10,btnUpdate,btnRemove);
                tm.setBox(box);

                tableList.add(tm);
            }

            tblRentItemDetails.setItems(tableList);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
