package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;
import util.CrudUtil;
import util.Validate;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class UpdateRentFormController {
    public JFXTextField txtAddDays;
    public JFXButton btnAdd;
    public AnchorPane dashBoardContext;
    LinkedHashMap<JFXTextField, Pattern> map = new LinkedHashMap<>();
    public static String rentId;
    public static String itemCode;

    public void initialize(){
        // pattern
        Pattern pattern = Pattern.compile("^[0-9]+$");

        map.put(txtAddDays,pattern);

    }

    public void btnAddOnAction(ActionEvent actionEvent) {

        addRentDays();

    }

    private void addRentDays() {
        Alert alert=new Alert(Alert.AlertType.INFORMATION,"Successfully Added days.");
        alert.showAndWait();

        try {
            ResultSet resultSet = CrudUtil.executeQuery("SELECT * FROM `rent detail`WHERE rentId=? && itemCode=?", rentId,itemCode);
            if (resultSet.next()){
                String string = resultSet.getString(5);
                LocalDate date= LocalDate.parse(string);
                LocalDate returnDate = date.plusDays(Integer.parseInt(txtAddDays.getText()));

                // new rent Days
                int rentDays=resultSet.getInt(4)+Integer.parseInt(txtAddDays.getText());

                // qty
                int qty = resultSet.getInt(3);

                double unitPrice=0;

                ResultSet resultSet1 = CrudUtil.executeQuery("SELECT item.sellingPrice FROM item WHERE code=?", itemCode);
                if (resultSet1.next()){
                    unitPrice = resultSet1.getDouble(1);
                }

                // Total price for rent item
                double totalPrice=rentDays*qty*unitPrice;

                // update details
                CrudUtil.executeUpdate("UPDATE `rent detail` SET rentDays=?,returnDate=?,price=? WHERE rentId=? && itemCode=?",rentDays,returnDate,totalPrice,rentId,itemCode);
            }

            Stage stage = (Stage) dashBoardContext.getScene().getWindow();
            stage.getOnCloseRequest().handle(new WindowEvent(stage,WindowEvent.WINDOW_CLOSE_REQUEST));
            stage.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void textFields_Key_Released(KeyEvent keyEvent) {
        new Validate().validate(map, btnAdd);
        if (keyEvent.getCode()== KeyCode.ENTER) {
            addRentDays();
        }
    }
}
