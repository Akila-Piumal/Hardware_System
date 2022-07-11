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
import model.Order;
import model.OrderDetails;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import util.Validate;
import view.TM.CartTM;

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

public class PlaceOrderFormController {
    public TableView<CartTM> tblCart;
    public AnchorPane dashBoardContext;
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
    public Label lblOrderId;
    public Label lblTotalCost;
    public JFXButton btnAddToCart;
    ObservableList<CartTM> tableList = FXCollections.observableArrayList();
    LinkedHashMap<JFXTextField, Pattern> map = new LinkedHashMap<>();

    public void initialize() {

        tblCart.getColumns().get(0).setCellValueFactory(new PropertyValueFactory("code"));
        tblCart.getColumns().get(1).setCellValueFactory(new PropertyValueFactory("description"));
        tblCart.getColumns().get(2).setCellValueFactory(new PropertyValueFactory("unitPrice"));
        tblCart.getColumns().get(3).setCellValueFactory(new PropertyValueFactory("qty"));
        tblCart.getColumns().get(4).setCellValueFactory(new PropertyValueFactory("totalCost"));
        tblCart.getColumns().get(5).setCellValueFactory(new PropertyValueFactory("btn"));

        btnAddToCart.setDisable(true);

        // Pattern of Qty
        Pattern pattern = Pattern.compile("^[1-9][0-9]*$");

        map.put(txtQty,pattern);

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

        // Load Item Codes to the Combo Box
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

        // Set The Order Id
        setOrderId();

    }

    // Set Current Order Id To the Label
    private void setOrderId() {

        try {
            String lastOrderId = OrderCrudController.getLastOrderId();
            String substring = lastOrderId.substring(1);
            int i = Integer.parseInt(substring) + 1;
            String s = String.valueOf(i);

            if (s.length() == 1) {
                lblOrderId.setText("D00" + s);
            } else if (s.length() == 2) {
                lblOrderId.setText("D0" + s);
            } else {
                lblOrderId.setText("D" + s);
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Load Item Codes to the Combo Box
    private void loadItemCodes() {
        try {
            ArrayList<String> codes = ItemCrudController.getSellingItemCodes("For Sale");
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

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Back to the Home
    public void btnHomeOnAction(ActionEvent actionEvent) throws IOException {
        dashBoardContext.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("../view/HomeForm.fxml"));
        dashBoardContext.getChildren().add(parent);
    }

    // Add New Customer
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
                    Parent parent = FXMLLoader.load(getClass().getResource("../view/PlaceOrderForm.fxml"));
                    dashBoardContext.getChildren().clear();
                    dashBoardContext.getChildren().add(parent);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        stage.show();
    }

    // Add items to the Cart Table
    public void btnAddToCartOnAction(ActionEvent actionEvent) {
        double unitPrice = Double.parseDouble(txtUnitPrice.getText());
        int qty = Integer.parseInt(txtQty.getText());
        double totalCost = unitPrice * qty;

        CartTM isExists = isExists(cmbItemId.getValue());

        if (isExists != null) {
            for (CartTM temp : tableList) {
                if (temp.equals(isExists)) {
                    temp.setQty((temp.getQty() + qty));
                    temp.setTotalCost(temp.getTotalCost() + totalCost);
                }
            }
        } else {
            Button btn = new Button("Delete");

            CartTM tm = new CartTM(
                    cmbItemId.getValue(),
                    txtDescription.getText(),
                    unitPrice,
                    qty,
                    totalCost,
                    btn
            );

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
    }

    // Calculate The Total Cost of the Order
    private void calculateTotal() {
        double total = 0;
        for (CartTM tm : tableList) {
            total += tm.getTotalCost();
        }
        lblTotalCost.setText(String.valueOf(total));
    }

    // Check That the Selected item Already in the Cart
    private CartTM isExists(String itemCode) {
        for (CartTM tm : tableList) {
            if (tm.getCode().equals(itemCode)) {
                return tm;
            }
        }
        return null;
    }

    // Place The Order
    public void btnPlaceOrderOnAction(ActionEvent actionEvent) throws SQLException {
        LocalDate date = LocalDate.now();
        String time = LocalTime.now().getHour() + ":" + LocalTime.now().getMinute();

        Order order = new Order(lblOrderId.getText(), date, time, Double.parseDouble(lblTotalCost.getText()), cmbCustomerId.getValue());

        ArrayList<OrderDetails> details = new ArrayList<>();
        for (CartTM tm : tableList) {
            details.add(new OrderDetails(lblOrderId.getText(), tm.getCode(), tm.getQty(), tm.getTotalCost()));
        }

        Connection connection = null;

        try {

            connection = DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            if (new OrderCrudController().saveOrder(order)) {
                if (new OrderCrudController().saveOrderDetails(details)) {
                    connection.commit();
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Order Successful."+"\nNeed to Print a bill?", ButtonType.YES,ButtonType.NO);
                    Optional<ButtonType> buttonType = alert.showAndWait();
                    if (buttonType.get().equals(ButtonType.YES)){
                        //Print bill
                        report();

                    }

                    dashBoardContext.getChildren().clear();
                    Parent parent = FXMLLoader.load(getClass().getResource("../view/PlaceOrderForm.fxml"));
                    dashBoardContext.getChildren().add(parent);
                } else {
                    connection.rollback();
                    new Alert(Alert.AlertType.ERROR, "Error!").show();
                }

            } else {
                connection.rollback();
                new Alert(Alert.AlertType.ERROR, "Error!").show();
            }
        } catch (SQLException | ClassNotFoundException | IOException e) {
            System.out.println(e);
        } finally {
            connection.setAutoCommit(true);
        }
    }

    // view Report
    private void report() {
        //We should gather information to send to the report
        String customerID =cmbCustomerId.getValue();
        String customerName = txtCustomerName.getText();
        String orderId = lblOrderId.getText();
        Double totalCost= Double.valueOf(lblTotalCost.getText());

        HashMap paramMap = new HashMap();

        paramMap.put("id", customerID);// id = report param name // customerID = UI typed value
        paramMap.put("name", customerName);
        paramMap.put("oid", orderId);
        paramMap.put("subTotal",totalCost);

        ObservableList<CartTM> tableRecords = tblCart.getItems();

        try {
            JasperReport compileReport = (JasperReport) JRLoader.loadObject(this.getClass().getResource("/view/reports/placeOrderReport.jasper"));
            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, paramMap, new JRBeanCollectionDataSource(tableRecords));
            JasperViewer.viewReport(jasperPrint, false);

        } catch (JRException e) {
            e.printStackTrace();
        }
    }

    public void textFields_Key_Released(KeyEvent keyEvent) {

        new Validate().validate(map,btnAddToCart);
    }
}
