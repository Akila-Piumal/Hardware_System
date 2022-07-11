package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import model.Item;
import model.ReturnOrder;
import model.ReturnOrderDetails;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import util.Validate;
import view.TM.CartTMReturn;

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

public class ReturnFormController {
    public JFXComboBox<String> cmbItemId;
    public JFXTextField txtDescription;
    public JFXTextField txtQtyOnHand;
    public JFXTextField txtUnitPrice;
    public JFXTextField txtReturnQty;
    public TableView<CartTMReturn> tblCart;
    public Label lblReturnId;
    public Label lblTotalCost;
    public AnchorPane dashBoardContext;
    public JFXButton btnAddToCart;
    ObservableList<CartTMReturn> tableList = FXCollections.observableArrayList();
    LinkedHashMap<JFXTextField, Pattern> map = new LinkedHashMap<>();

    public void initialize() {

        tblCart.getColumns().get(0).setCellValueFactory(new PropertyValueFactory("itemCode"));
        tblCart.getColumns().get(1).setCellValueFactory(new PropertyValueFactory("description"));
        tblCart.getColumns().get(2).setCellValueFactory(new PropertyValueFactory("unitPrice"));
        tblCart.getColumns().get(3).setCellValueFactory(new PropertyValueFactory("returnQty"));
        tblCart.getColumns().get(4).setCellValueFactory(new PropertyValueFactory("totalCost"));
        tblCart.getColumns().get(5).setCellValueFactory(new PropertyValueFactory("btnDelete"));


        // load item codes to the combo box
        loadItemCodes();

        cmbItemId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                Item item = ItemCrudController.getItem(newValue);
                txtDescription.setText(item.getDescription());
                txtQtyOnHand.setText(String.valueOf(item.getQtyOnHand()));
                txtUnitPrice.setText(String.valueOf(item.getBuyingPrice()));
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        // Set Current Return Id To the Label
        setReturnId();

        btnAddToCart.setDisable(true);

        // pattern of qty
        Pattern pattern = Pattern.compile("^[1-9][0-9]*$");

        map.put(txtReturnQty,pattern);

    }

    // Set Current Return Id To the Label
    private void setReturnId() {
        try {
            String lastReturnOrderId = ReturnCrudController.getLastReturnOrderId();
            String substring = lastReturnOrderId.substring(1);
            int i = Integer.parseInt(substring) + 1;
            String s = String.valueOf(i);

            if (s.length() == 1) {
                lblReturnId.setText("B00" + s);
            } else if (s.length() == 2) {
                lblReturnId.setText("B0" + s);
            } else {
                lblReturnId.setText("B" + s);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // load item codes to the combo box
    private void loadItemCodes() {
        try {
            ArrayList<String> codes = ItemCrudController.getCodes();
            ObservableList<String> obList = FXCollections.observableArrayList();
            obList.setAll(codes);
            cmbItemId.setItems(obList);
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

    // Add items to the Cart Table
    public void btnAddToCartOnAction(ActionEvent actionEvent) {
        double unitPrice = Double.parseDouble(txtUnitPrice.getText());
        int qty = Integer.parseInt(txtReturnQty.getText());
        double totalCost = unitPrice * qty;

        CartTMReturn isExists = isExists(cmbItemId.getValue());
        if (isExists != null) {
            for (CartTMReturn temp : tableList) {
                if (temp.equals(isExists)) {
                    temp.setReturnQty(temp.getReturnQty() + qty);
                    temp.setTotalCost(temp.getTotalCost() + totalCost);
                }
            }
        } else {
            Button btn = new Button("Delete");

            CartTMReturn tm = new CartTMReturn(cmbItemId.getValue(), txtDescription.getText(), unitPrice, qty, totalCost, btn);

            btn.setOnAction(event -> {
                tableList.remove(tm);
                calculateTotal();
            });

            tableList.add(tm);
            tblCart.setItems(tableList);
        }
        tblCart.refresh();
        calculateTotal();
        txtReturnQty.clear();
    }

    // Calculate Total Cost
    private void calculateTotal() {
        double totalCost = 0;
        for (CartTMReturn tm : tableList) {
            totalCost += tm.getTotalCost();
        }
        lblTotalCost.setText(String.valueOf(totalCost));
    }

    // Check That the Selected item Already in the Cart
    private CartTMReturn isExists(String itemId) {
        for (CartTMReturn tm : tableList) {
            if (tm.getItemCode().equals(itemId)) {
                return tm;
            }
        }
        return null;
    }

    public void btnPlaceReturnOnAction(ActionEvent actionEvent) throws SQLException {
        LocalDate date = LocalDate.now();
        String time = LocalTime.now().getHour() + ":" + LocalTime.now().getMinute();

        ReturnOrder returnOrder = new ReturnOrder(lblReturnId.getText(), date, Double.parseDouble(lblTotalCost.getText()));

        ArrayList<ReturnOrderDetails> details = new ArrayList<>();
        for (CartTMReturn tm : tableList) {
            details.add(new ReturnOrderDetails(lblReturnId.getText(), tm.getItemCode(), tm.getReturnQty(), tm.getTotalCost()));

        }
        Connection connection = null;

        try {
            connection = DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            if (new ReturnCrudController().saveReturnOrder(returnOrder)) {
                if (new ReturnCrudController().saveReturnOrderDetails(details)) {
                    connection.commit();
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Return Successful."+"\nNeed to Print a bill?", ButtonType.YES,ButtonType.NO);
                    Optional<ButtonType> buttonType = alert.showAndWait();

                    if (buttonType.get().equals(ButtonType.YES)){
                        //Print bill
                        report();

                    }

                    dashBoardContext.getChildren().clear();
                    Parent parent = FXMLLoader.load(getClass().getResource("../view/ReturnForm.fxml"));
                    dashBoardContext.getChildren().add(parent);
                } else {
                    connection.rollback();
                    new Alert(Alert.AlertType.ERROR, "Error").show();
                }
            } else {
                connection.rollback();
                new Alert(Alert.AlertType.ERROR, "Error").show();
            }
        } catch (SQLException | ClassNotFoundException | IOException e) {
            System.out.println(e);
        } finally {
            connection.setAutoCommit(true);
        }
    }

    private void report() {

        // gather information for parameters
        String returnId=lblReturnId.getText();
        Double totalCost=Double.parseDouble(lblTotalCost.getText());

        HashMap map=new HashMap();
        map.put("ReturnId",returnId);
        map.put("totalCost",totalCost);

        ObservableList<CartTMReturn> tableRecords=tblCart.getItems();

        try {
            JasperReport compileReport = (JasperReport) JRLoader.loadObject(this.getClass().getResource("/view/reports/ReturnReport.jasper"));
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
