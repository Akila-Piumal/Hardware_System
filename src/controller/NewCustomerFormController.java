package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import javafx.stage.WindowEvent;
import model.Customer;
import util.CrudUtil;
import util.Validate;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class NewCustomerFormController {

    public AnchorPane dashBoardContext;
    public JFXButton btnAdd;
    public JFXTextField txtCustomerId;
    public JFXTextField txtCustomerName;
    public JFXTextField txtCustomerAddress;
    public JFXTextField txtContact;
    LinkedHashMap<JFXTextField, Pattern> map = new LinkedHashMap<>();

    public void initialize(){
        setCustomerId();

        // Create Patterns
        Pattern namePattern = Pattern.compile("^[A-z ]{3,15}$");
        Pattern addressPattern = Pattern.compile("^[A-z/,0-9 ]{4,30}$");
        Pattern contactPattern=Pattern.compile("^(07)(7|8|6|5|4|1|0|2)[0-9]{7}$");

        map.put(txtCustomerName,namePattern);
        map.put(txtCustomerAddress,addressPattern);
        map.put(txtContact,contactPattern);
    }


    private void setCustomerId() {

        try {
            String lastCustomerId = CustomerCrudController.getLastCustomerId();
            int i = Integer.parseInt(lastCustomerId.substring(1))+1;
            String s=String.valueOf(i);

            if (s.length()==1){
                txtCustomerId.setText("C00"+s);
            }else if (s.length()==2){
                txtCustomerId.setText("C0"+s);
            }else {
                txtCustomerId.setText("C"+s);
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Save Customer
    public void btnSaveCustomerOnAction(ActionEvent actionEvent) {
        saveCustomer();
    }

    private void saveCustomer() {
        Customer c=new Customer(txtCustomerId.getText(),txtCustomerName.getText(),txtCustomerAddress.getText(),txtContact.getText());
        try {
            if (CustomerCrudController.saveCustomer(c)) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Saved");
                alert.showAndWait();
                Stage stage = (Stage) dashBoardContext.getScene().getWindow();
                stage.getOnCloseRequest().handle(new WindowEvent(stage,WindowEvent.WINDOW_CLOSE_REQUEST));
                stage.close();

            }else{
                new Alert(Alert.AlertType.WARNING,"Something went Wrong").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void textFields_Key_Released(KeyEvent keyEvent) {

        Object response = new Validate().validate(map, btnAdd);

        // if ENTER key Pressed
        if (keyEvent.getCode()== KeyCode.ENTER) {
            if (response instanceof TextField){
                TextField textField= (TextField) response;
                textField.requestFocus();
            }else if(response instanceof Boolean){
                saveCustomer();
            }
        }
    }
}
