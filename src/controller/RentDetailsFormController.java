package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import model.RentDetails;
import util.CrudUtil;
import view.TM.RentDetailsTM;
import view.TM.RentItemDetailsTM;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class RentDetailsFormController {
    public TableView<RentDetailsTM> tblRentDetails;
    public AnchorPane dashBoardContext;
    ObservableList<RentDetailsTM> obList= FXCollections.observableArrayList();

    public void initialize(){

        tblRentDetails.getColumns().get(0).setCellValueFactory(new PropertyValueFactory("rentId"));
        tblRentDetails.getColumns().get(1).setCellValueFactory(new PropertyValueFactory("date"));
        tblRentDetails.getColumns().get(2).setCellValueFactory(new PropertyValueFactory("time"));
        tblRentDetails.getColumns().get(3).setCellValueFactory(new PropertyValueFactory("totalCost"));
        tblRentDetails.getColumns().get(4).setCellValueFactory(new PropertyValueFactory("customerId"));
        tblRentDetails.getColumns().get(5).setCellValueFactory(new PropertyValueFactory("btnDetails"));
        tblRentDetails.getColumns().get(6).setCellValueFactory(new PropertyValueFactory("option"));

        loadRentDetails();

    }

    private void loadRentDetails() {

        try {
            ResultSet resultSet = CrudUtil.executeQuery("SELECT * FROM rent");
            while (resultSet.next()){

                Button btnDetails=new Button("details");
                Button btnDelete=new Button("Delete");

                RentDetailsTM tm=new RentDetailsTM(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),resultSet.getDouble(4),resultSet.getString(5),btnDetails,btnDelete);
                obList.add(tm);

                // Add Action to details button
                btnDetails.setStyle("-fx-background-color: #00b894");
                btnDetails.setOnAction(event -> {
                    RentItemDetailsFormController.rentId=tm.getRentId();
                    try {
                        Parent parent = FXMLLoader.load(getClass().getResource("../view/RentItemDetailsForm.fxml"));
                        Scene scene=new Scene(parent);
                        Stage stage=new Stage();
                        stage.setScene(scene);
                        stage.show();
                        stage.setOnCloseRequest(event1 -> {
                            try {
                                Parent parent1 = FXMLLoader.load(getClass().getResource("../view/RentDetailsForm.fxml"));
                                dashBoardContext.getChildren().clear();
                                dashBoardContext.getChildren().add(parent1);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                });

                // Add Action to delete Button
                btnDelete.setStyle("-fx-background-color: red");
                btnDelete.setOnAction(event -> {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are You Sure ? ", ButtonType.YES, ButtonType.NO);
                    Optional<ButtonType> buttonType = alert.showAndWait();
                    if (buttonType.get().equals(ButtonType.YES)) {
                        try {
                            obList.remove(tm);
                            ArrayList<RentItemDetailsTM> rentItemDetails = new RentCrudController().getRentItemDetails(tm.getRentId());
                            for (RentItemDetailsTM tm1:rentItemDetails) {
                                CrudUtil.executeUpdate("UPDATE item SET qtyOnHand=qtyOnHand+? WHERE code=?", tm1.getQty(),tm1.getItemCode());
                            }
                            CrudUtil.executeUpdate("DELETE FROM rent WHERE id=?",tm.getRentId());
                        } catch (SQLException e) {
                            e.printStackTrace();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                });

            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        tblRentDetails.setItems(obList);
    }

    public void btnHomeOnAction(ActionEvent actionEvent) throws IOException {
        dashBoardContext.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("../view/HomeForm.fxml"));
        dashBoardContext.getChildren().add(parent);
    }

}
