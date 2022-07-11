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
import model.*;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import util.Validate;
import view.TM.CartTM;
import view.TM.CartTMSupply;

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

public class SupplyOrderFormController {
    public JFXComboBox<String> cmbSupplierId;
    public JFXComboBox<String> cmbItemCode;
    public JFXTextField txtSupplierName;
    public JFXTextField txtSupplierAddress;
    public JFXTextField txtCompany;
    public JFXTextField txtSearchByName;
    public JFXTextField txtDescription;
    public JFXTextField txtBuyingPrice;
    public JFXTextField txtSellingPrice;
    public JFXTextField txtQtyOnHand;
//    public JFXTextField txtUnitPrice;
    public JFXTextField txtQty;
    public JFXTextField txtContact;
    public Label lblSupplyId;
    public Label lblTotalCost;
    public TableView<CartTMSupply> tblCart;
    public AnchorPane dashBoardContext;
    public JFXButton btnAddToCart;
    LinkedHashMap<JFXTextField, Pattern> map = new LinkedHashMap<>();

    public void initialize(){

        tblCart.getColumns().get(0).setCellValueFactory(new PropertyValueFactory("code"));
        tblCart.getColumns().get(1).setCellValueFactory(new PropertyValueFactory("description"));
        tblCart.getColumns().get(2).setCellValueFactory(new PropertyValueFactory("buyingPrice"));
        tblCart.getColumns().get(3).setCellValueFactory(new PropertyValueFactory("sellingPrice"));
        tblCart.getColumns().get(4).setCellValueFactory(new PropertyValueFactory("qty"));
        tblCart.getColumns().get(5).setCellValueFactory(new PropertyValueFactory("totalCost"));
        tblCart.getColumns().get(6).setCellValueFactory(new PropertyValueFactory("btn"));

        // load Supplier ids to the combo box
        loadSupplierIds();

        // Adding A Listener to the SupplierId combo box and set The values to the  textFields
        cmbSupplierId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                Supplier supplier = SupplierCrudController.getSupplier(newValue);
                txtSupplierName.setText(supplier.getName());
                txtSupplierAddress.setText(supplier.getAddress());
                txtCompany.setText(supplier.getCompany());
                txtContact.setText(supplier.getContact());

                txtSupplierName.setDisable(false);
                txtSupplierAddress.setDisable(false);
                txtCompany.setDisable(false);
                txtContact.setDisable(false);
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        // Load Item Codes to the Combo Box
        loadItemCodes();

        // Adding A Listener to the ItemId combo box and set The values to the  textFields
        cmbItemCode.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                Item item = ItemCrudController.getItem(newValue);
                txtDescription.setText(item.getDescription());
                txtBuyingPrice.setText(String.valueOf(item.getBuyingPrice()));
                txtSellingPrice.setText(String.valueOf(item.getSellingPrice()));
                txtQtyOnHand.setText(String.valueOf(item.getQtyOnHand()));

                txtDescription.setDisable(false);
                txtSellingPrice.setDisable(false);
                txtBuyingPrice.setDisable(false);
                txtQtyOnHand.setDisable(false);

            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        // Search Customer By Name
        txtSearchByName.setOnKeyReleased(event -> {
            String name = txtSearchByName.getText();
            try {
                Supplier supplier = SupplierCrudController.getCustomerByName(name);
                if (supplier != null) {
                    cmbSupplierId.getSelectionModel().select(supplier.getId());
                }

            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        // Set The Order Id
        setSupplyId();

        // load new item to the cart
        tblCart.setItems(tableList);

        // Calculate Total Cost
        calculateTotalCost();

        btnAddToCart.setDisable(true);

        // Pattern of Qty
        Pattern pattern = Pattern.compile("^[1-9][0-9]*$");

        map.put(txtQty,pattern);

    }

    // Calculate The Total Cost of the Order
    private void calculateTotalCost() {
        double totalCost=0;
        for (CartTMSupply tm:tableList) {

            totalCost+=tm.getTotalCost();

        }
        lblTotalCost.setText(String.valueOf(totalCost));
    }

    // Set Current Supply-Order Id To the Label
    private void setSupplyId() {
        try {
            String lastId = SupplierOrderCrudController.getLastId();
            String toSubString=lastId.substring(1);
            int i=Integer.parseInt(toSubString)+1;
            String s=String.valueOf(i);

            if (s.length()==1){
                lblSupplyId.setText("X00"+s);
            }else if (s.length()==2){
                lblSupplyId.setText("X0"+s);
            }else {
                lblSupplyId.setText("X"+s);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Load Item Codes to the Combo Box
    private void loadItemCodes() {
        ObservableList<String> obList= FXCollections.observableArrayList();
        try {
            ArrayList<String> itemCodes = ItemCrudController.getCodes();
            obList.addAll(itemCodes);
            cmbItemCode.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // load Supplier ids to the combo box
    private void loadSupplierIds() {
        ObservableList<String> obList= FXCollections.observableArrayList();
        try {
            ArrayList<String> supplierIds = SupplierCrudController.getSupplierIds();
            obList.addAll(supplierIds);
            cmbSupplierId.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Back to the Home
    public void btnHomeOnAction(ActionEvent actionEvent) throws IOException {
        dashBoardContext.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("../view/HomeForm.fxml"));
        dashBoardContext.getChildren().add(parent);
    }

    // Add New Supplier
    public void btnNewSupplierOnAction(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("../view/NewSupplierForm.fxml"));
        Scene scene=new Scene(parent);
        Stage stage=new Stage();
        stage.setScene(scene);
        stage.initStyle(StageStyle.UTILITY);
        stage.setTitle("New Supplier");
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                try {
                    Parent parent = FXMLLoader.load(getClass().getResource("../view/SupplyOrderForm.fxml"));
                    dashBoardContext.getChildren().clear();
                    dashBoardContext.getChildren().add(parent);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        stage.show();
    }

    // Create Observable list to store Details of the table
    public static ObservableList<CartTMSupply> tableList=FXCollections.observableArrayList();

    // Add items to the Cart Table
    public void btnAddToCartOnAction(ActionEvent actionEvent) {
        double buyingPrice=Double.parseDouble(txtBuyingPrice.getText());
        int qty=Integer.parseInt(txtQty.getText());
        double totalCost=buyingPrice*qty;

        CartTMSupply isExists=isExists(cmbItemCode.getValue());
        if (isExists!=null){
            for (CartTMSupply tm:tableList) {
                if (tm.equals(isExists)){
                    tm.setQty(tm.getQty()+qty);
                    tm.setTotalCost(tm.getTotalCost()+totalCost);
                }
            }
        }else{
            Button btn = new Button("Delete");

            CartTMSupply tm = new CartTMSupply(
                    cmbItemCode.getValue(),
                    txtDescription.getText(),
                    buyingPrice,
                    Double.parseDouble(txtSellingPrice.getText()),
                    qty,
                    totalCost,
                    btn
            );

            btn.setOnAction(e->{
                tableList.remove(tm);
                calculateTotalCost();
            });

            tableList.add(tm);
            tblCart.setItems(tableList);
        }
        txtQty.clear();
        tblCart.refresh();
        calculateTotalCost();
    }

    // Check That the Selected item Already in the Cart
    private CartTMSupply isExists(String itemCode) {
        for (CartTMSupply tm:tableList) {
            if (tm.getCode().equals(itemCode)){
                return tm;
            }
        }
        return null;
    }

    // Add New Item
    public void btnNewItemOnAction(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("../view/NewItemForm.fxml"));
        Scene scene=new Scene(parent);
        Stage stage=new Stage();
        stage.setScene(scene);
        stage.initStyle(StageStyle.UTILITY);
        stage.setTitle("New Item");
        stage.setOnCloseRequest(event -> {
            try {
                Parent parent1 = FXMLLoader.load(getClass().getResource("../view/SupplyOrderForm.fxml"));
                dashBoardContext.getChildren().clear();
                dashBoardContext.getChildren().add(parent1);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        stage.show();
    }

    // Place The Order
    public void btnPlaceOrderOnAction(ActionEvent actionEvent) throws SQLException {

        LocalDate date = LocalDate.now();
        String time = LocalTime.now().getHour() + ":" + LocalTime.now().getMinute();

        SupplyOrder order=new SupplyOrder(lblSupplyId.getText(),date,time,Double.parseDouble(lblTotalCost.getText()),cmbSupplierId.getValue());

        ArrayList<SupplyOrderDetails> details=new ArrayList<>();
        for (CartTMSupply tm:tableList) {
            details.add(new SupplyOrderDetails(lblSupplyId.getText(),tm.getCode(),tm.getQty(),tm.getTotalCost()));
        }

        Connection connection=null;

        try {
            connection= DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            if (new SupplierOrderCrudController().saveSupplyOrder(order)) {
                if (new SupplierOrderCrudController().saveSupplyOrderDetails(details)) {
                    connection.commit();
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Supply Successful."+"\nNeed to Print a bill?", ButtonType.YES,ButtonType.NO);
                    Optional<ButtonType> buttonType = alert.showAndWait();

                    if (buttonType.get().equals(ButtonType.YES)){
                        //Print bill
                        report();

                    }

                    lblTotalCost.setText("0.0");
                    tableList.clear();
                    dashBoardContext.getChildren().clear();
                    Parent parent = FXMLLoader.load(getClass().getResource("../view/SupplyOrderForm.fxml"));
                    dashBoardContext.getChildren().add(parent);


                }else{
                    connection.rollback();
                    new Alert(Alert.AlertType.ERROR,"Error").show();
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
        String supplierId=cmbSupplierId.getValue();
        String supplierName=txtSupplierName.getText();
        String company=txtCompany.getText();
        String supplyId=lblSupplyId.getText();
        Double totalCost= Double.valueOf(lblTotalCost.getText());

        HashMap map=new HashMap();
        map.put("supplierId",supplierId);
        map.put("supplierName",supplierName);
        map.put("company",company);
        map.put("supplyId",supplyId);
        map.put("totalCost",totalCost);

        ObservableList<CartTMSupply> items = tblCart.getItems();

        try {

            JasperReport compileReport = (JasperReport) JRLoader.loadObject(this.getClass().getResource("/view/reports/SupplyOrderReport.jasper"));
            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, map, new JRBeanCollectionDataSource(items));
            JasperViewer.viewReport(jasperPrint,false);

        } catch (JRException e) {
            e.printStackTrace();
        }
    }

    public void textFields_Key_Released(KeyEvent keyEvent) {
        new Validate().validate(map,btnAddToCart);
    }
}
