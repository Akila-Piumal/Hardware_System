package controller;

import com.sun.org.apache.xpath.internal.operations.Or;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import util.CrudUtil;
import view.TM.OrderTM;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class HomeFormController {
    public Label lblTodayOrders;
    public Label lblTodayIncome;
    public AnchorPane dashBoardContext;
    public TableView<OrderTM> tblOrders;
    public Label lblTodayRents;
    ObservableList<OrderTM> obList= FXCollections.observableArrayList();

    public void initialize(){
        tblOrders.getColumns().get(0).setCellValueFactory(new PropertyValueFactory("orderId"));
        tblOrders.getColumns().get(1).setCellValueFactory(new PropertyValueFactory("date"));
        tblOrders.getColumns().get(2).setCellValueFactory(new PropertyValueFactory("time"));
        tblOrders.getColumns().get(3).setCellValueFactory(new PropertyValueFactory("customerId"));
        tblOrders.getColumns().get(4).setCellValueFactory(new PropertyValueFactory("cost"));

        loadTodayOrderCount();

        loadTodayIncome();

        loadLatestOrders();

        loadTodayRentCount();
    }

    private void loadTodayIncome() {

        try {
            double orderIncome=0;
            double rentIncome=0;

            ResultSet result = CrudUtil.executeQuery("SELECT SUM(`order`.cost) FROM `order`WHERE date=?", LocalDate.now());
            if (result.next()){
                orderIncome=result.getDouble(1);
            }

            ResultSet result2 = CrudUtil.executeQuery("SELECT SUM(rent.cost) FROM rent WHERE date=?", LocalDate.now());
            if (result2.next()){
                rentIncome=result2.getDouble(1);
            }

            lblTodayIncome.setText(String.valueOf(orderIncome+rentIncome));

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


//        String format = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
//        try {
//            ResultSet resultSet = CrudUtil.executeQuery("SELECT SUM(cost) FROM `order` WHERE date=?",format);
//            if (resultSet.next()){
//                String income = resultSet.getString(1);
//                if (income!=null){
//                    lblTodayIncome.setText(income);
//                }else{
//                    lblTodayIncome.setText("0");
//                }
//            }
//        } catch (SQLException | ClassNotFoundException e) {
//            e.printStackTrace();
//        }

    }

    // set number of orders today
    private void loadTodayOrderCount() {
        String format = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        try {
            ResultSet resultSet = CrudUtil.executeQuery("SELECT * FROM `order` WHERE date=?", format);
            int count=0;
            while (resultSet.next()){
                count++;
            }
            lblTodayOrders.setText(String.valueOf(count));
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // set number of rents today
    private void loadTodayRentCount() {
        String format = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        try {
            ResultSet resultSet = CrudUtil.executeQuery("SELECT * FROM rent WHERE date=?", format);
            int count=0;
            while (resultSet.next()){
                count++;
            }
            lblTodayRents.setText(String.valueOf(count));
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Load Latest Orders To the Table
    private void loadLatestOrders() {
        try {
            ResultSet resultSet = CrudUtil.executeQuery("SELECT * FROM `order` ORDER BY id DESC LIMIT 10");

            while (resultSet.next()){
                OrderTM tm=new OrderTM(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),resultSet.getString(5),resultSet.getDouble(4));
                obList.add(tm);
            }
            tblOrders.setItems(obList);
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    // Log out to the Login page
    public void btnLogOutOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage =(Stage) dashBoardContext.getScene().getWindow();
        stage.close();
        Parent parent= FXMLLoader.load(getClass().getResource("../view/LoginForm.fxml"));
        Scene scene=new Scene(parent);
        Stage stage2=new Stage();
        stage2.setScene(scene);
        stage2.show();
    }
}
