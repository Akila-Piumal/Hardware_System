package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import model.Customer;
import model.Item;
import model.Rent;
import model.RentDetails;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import util.Validate;
import view.TM.CartTMRent;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Optional;
import java.util.regex.Pattern;

public class RentFormController {
    public JFXComboBox<String> cmbCustomerId;
    public JFXTextField txtCustomerName;
    public JFXTextField txtCustomerAddress;
    public JFXTextField txtContact;
    public JFXTextField txtSearchByName;
    public JFXComboBox<String> cmbItemId;
    public JFXTextField txtDescription;
    public JFXTextField txtQtyOnHand;
    public JFXTextField txtUnitPrice;
    public JFXTextField txtQty;
    public JFXTextField txtRentDays;
    public Label lblRentId;
    public Label lblTotalCost;
    public TableView<CartTMRent> tblCart;
    public AnchorPane dashBoardContext;
    public JFXButton btnAddToCart;
    LinkedHashMap<JFXTextField, Pattern> map = new LinkedHashMap<>();

    public void initialize(){

        tblCart.getColumns().get(0).setCellValueFactory(new PropertyValueFactory("itemCode"));
        tblCart.getColumns().get(1).setCellValueFactory(new PropertyValueFactory("description"));
        tblCart.getColumns().get(2).setCellValueFactory(new PropertyValueFactory("unitPrice"));
        tblCart.getColumns().get(3).setCellValueFactory(new PropertyValueFactory("qty"));
        tblCart.getColumns().get(4).setCellValueFactory(new PropertyValueFactory("rentDays"));
        tblCart.getColumns().get(5).setCellValueFactory(new PropertyValueFactory("totalCost"));
        tblCart.getColumns().get(6).setCellValueFactory(new PropertyValueFactory("btn"));

        // load Customer ids to the combo box
        loadCustomerIds();

        // Adding A Listener to the CustomerId combo box and set The values to the  textFields
        cmbCustomerId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {

            try {
                Customer customer = CustomerCrudController.getCustomer(newValue);
                txtCustomerName.setText(customer.getName());
                txtCustomerAddress.setText(customer.getAddress());
                txtContact.setText(customer.getContact());
                txtCustomerName.setDisable(false);
                txtCustomerAddress.setDisable(false);
                txtContact.setDisable(false);

            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        // load Item Codes to the combo Box
        loadItemCodes();

        // Adding A Listener to the ItemId combo box and set The values to the  textFields
        cmbItemId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                Item item = ItemCrudController.getItem(newValue);
                txtDescription.setText(item.getDescription());
                txtUnitPrice.setText(String.valueOf(item.getSellingPrice()));
                txtQtyOnHand.setText(String.valueOf(item.getQtyOnHand()));
                txtDescription.setDisable(false);
                txtUnitPrice.setDisable(false);
                txtQtyOnHand.setDisable(false);
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        // Set The Rent Id
        setRentId();

        // Search Customer By Name
        txtSearchByName.setOnKeyReleased(event -> {
            String name = txtSearchByName.getText();
            try {
                Customer c = CustomerCrudController.getCustomerByName(name);
                if (c != null) {
                    cmbCustomerId.getSelectionModel().select(c.getId());
                }

            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        btnAddToCart.setDisable(true);

        // Patterns
        Pattern pattern = Pattern.compile("^[1-9][0-9]*$");

        map.put(txtRentDays,pattern);
        map.put(txtQty,pattern);

    }

     // Set Current Rent Id To the Label
    private void setRentId() {
        try {
            String lastRentId = RentCrudController.getLastRentId();
            String substring = lastRentId.substring(1);
            int i = Integer.parseInt(substring) + 1;
            String s = String.valueOf(i);

            if (s.length() == 1) {
                lblRentId.setText("R00" + s);
            } else if (s.length() == 2) {
                lblRentId.setText("R0" + s);
            } else {
                lblRentId.setText("R" + s);
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // load Item Codes to the combo Box
    private void loadItemCodes() {
        try {
            ArrayList<String> codes = ItemCrudController.getSellingItemCodes("For Rent");
            ObservableList<String> obList = FXCollections.observableArrayList();
            obList.setAll(codes);
            cmbItemId.setItems(obList);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    //load Customer ids to the combo box
    private void loadCustomerIds() {
        try {
            ObservableList<String> obList = FXCollections.observableArrayList();
            ArrayList<String> ids = CustomerCrudController.getIds();
            obList.setAll(ids);
            cmbCustomerId.setItems(obList);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Back to the Home
    public  void btnHomeOnAction(ActionEvent actionEvent) throws IOException {
        dashBoardContext.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("../view/HomeForm.fxml"));
        dashBoardContext.getChildren().add(parent);
    }

//    Save Customer
    public void btnNewCustomerOnAction(ActionEvent actionEvent) throws IOException {
        Parent load = FXMLLoader.load(getClass().getResource("../view/NewCustomerForm.fxml"));
        Scene scene = new Scene(load);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initStyle(StageStyle.UTILITY);
        stage.setTitle("New Customer");
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                try {
                    Parent parent = FXMLLoader.load(getClass().getResource("../view/PlaceRentForm.fxml"));
                    dashBoardContext.getChildren().clear();
                    dashBoardContext.getChildren().add(parent);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        stage.show();
    }

    ObservableList<CartTMRent> tableList=FXCollections.observableArrayList();

    // Add items to the Cart Table
    public void btnAddToCartOnAction(ActionEvent actionEvent) {
        double unitPrice=Double.parseDouble(txtUnitPrice.getText());
        int qty=Integer.parseInt(txtQty.getText());
        int rentDays=Integer.parseInt(txtRentDays.getText());
        double totalCost =unitPrice*qty*rentDays;

        CartTMRent isExists=isExists(cmbItemId.getValue());

        if (isExists!=null && isExists.getRentDays()==rentDays){
            for (CartTMRent tm:tableList) {
                if (tm.equals(isExists)){
                    tm.setQty(tm.getQty()+qty);
                    tm.setTotalCost(tm.getTotalCost()+totalCost);
                }
            }
        }else{
            Button btn=new Button("Delete");

            CartTMRent tm=new CartTMRent(cmbItemId.getValue(),txtDescription.getText(),unitPrice,qty,rentDays,totalCost,btn);

            btn.setOnAction(e -> {
                tableList.remove(tm);
                calculateTotal();
            });

            tableList.add(tm);
            tblCart.setItems(tableList);
        }
        tblCart.refresh();
        calculateTotal();
        txtQty.clear();
        txtRentDays.clear();
    }

    // Calculate The Total Cost of the Order
    private void calculateTotal() {
        double totalCost=0;
        for (CartTMRent tm:tableList) {
            totalCost+=tm.getTotalCost();
        }
        lblTotalCost.setText(String.valueOf(totalCost));
    }

    // Check That the Selected item Already in the Cart
    private CartTMRent isExists(String itemCode) {
        for (CartTMRent tm:tableList) {
            if (itemCode.equals(tm.getItemCode())){
                return tm;
            }
        }
        return null;
    }

    // Place The Rent
    public void btnPlaceRentOnAction(ActionEvent actionEvent) throws SQLException {

        LocalDate date = LocalDate.now();
        String time = LocalTime.now().getHour() + ":" + LocalTime.now().getMinute();

        Rent rent=new Rent(lblRentId.getText(),date,time,Double.parseDouble(lblTotalCost.getText()),cmbCustomerId.getValue());

        ArrayList<RentDetails> details=new ArrayList<>();
        for (CartTMRent tm:tableList) {
            LocalDate returnDate=date.plusDays(tm.getRentDays());
            details.add(new RentDetails(lblRentId.getText(),tm.getItemCode(),tm.getQty(),tm.getRentDays(),returnDate,tm.getTotalCost(),"In Rent"));
        }

        Connection connection=null;

        try {
            connection= DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            if (new RentCrudController().saveRent(rent)) {
                if (new RentCrudController().saveRentDetails(details)) {
                    connection.commit();
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Rent Successful."+"\nNeed to Print a bill?", ButtonType.YES,ButtonType.NO);
                    Optional<ButtonType> buttonType = alert.showAndWait();

                    if (buttonType.get().equals(ButtonType.YES)){
                        //Print bill
                        report();

                    }

                    dashBoardContext.getChildren().clear();
                    Parent parent = FXMLLoader.load(getClass().getResource("../view/PlaceRentForm.fxml"));
                    dashBoardContext.getChildren().add(parent);
                }else{
                    connection.rollback();
                    new Alert(Alert.AlertType.ERROR, "Error!").show();
                }
            }else{
                connection.rollback();
                new Alert(Alert.AlertType.ERROR, "Error!").show();
            }

        } catch (SQLException | ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }finally {
            connection.setAutoCommit(true);
        }

    }

    // View Report
    private void report() {

        //We should gather information to send to the report
        String customerId=cmbCustomerId.getValue();
        String customerName=txtCustomerName.getText();
        String rentId=lblRentId.getText();
        double totalCost= Double.parseDouble(lblTotalCost.getText());

        HashMap map=new HashMap();
        map.put("customerId",customerId);
        map.put("customerName",customerName);
        map.put("rentId",rentId);
        map.put("totalCost",totalCost);

        ObservableList<CartTMRent> tableRecords = tblCart.getItems();

        try {

            JasperReport compileReport = (JasperReport) JRLoader.loadObject(this.getClass().getResource("/view/reports/RentOrderReport.jasper"));
            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, map, new JRBeanCollectionDataSource(tableRecords));
            JasperViewer.viewReport(jasperPrint,false);

        } catch (JRException e) {
            e.printStackTrace();
        }
    }

    public void textFields_Key_Released(KeyEvent keyEvent) {
        new Validate().validate(map,btnAddToCart);
    }
}
