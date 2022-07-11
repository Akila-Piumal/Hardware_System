package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.Customer;
import model.Supplier;
import util.Validate;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class NewSupplierFormController {
    public AnchorPane dashBoardContext;
    public JFXTextField txtSupplierId;
    public JFXTextField txtSupplierName;
    public JFXTextField txtSupplierAddress;
    public JFXTextField txtCompany;
    public JFXTextField txtContact;
    public JFXButton btnAdd;
    LinkedHashMap<JFXTextField, Pattern> map = new LinkedHashMap<>();

    public void initialize(){
        setSupplierId();

        //Patterns for textFields
        Pattern namePattern = Pattern.compile("^[A-z ]{3,15}$");
        Pattern addressPattern = Pattern.compile("^[A-z/,0-9 ]{4,30}$");
        Pattern companyPattern = Pattern.compile("^[A-z0-9]{1,20}$");
        Pattern contactPattern=Pattern.compile("^(07)(7|8|6|5|4|1|0|2)[0-9]{7}$");

        // input textField and Pattern to map
        map.put(txtSupplierName,namePattern);
        map.put(txtSupplierAddress,addressPattern);
        map.put(txtCompany,companyPattern);
        map.put(txtContact,contactPattern);
    }

    private void setSupplierId() {
        try {
            String lastSupplierId = SupplierCrudController.getLastSupplierId();
            int i = Integer.parseInt(lastSupplierId.substring(1))+1;
            String s=String.valueOf(i);

            if (s.length()==1){
                txtSupplierId.setText("S00"+s);
            }else if (s.length()==2){
                txtSupplierId.setText("S0"+s);
            }else {
                txtSupplierId.setText("S"+s);
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void btnSaveSupplierOnAction(ActionEvent actionEvent) {
        saveSupplier();
    }

    private void saveSupplier() {
        Supplier s=new Supplier(txtSupplierId.getText(),txtSupplierName.getText(),txtSupplierAddress.getText(),txtContact.getText(),txtCompany.getText());
        try {
            if (SupplierCrudController.saveSupplier(s)) {
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

        // Validate the text Fields
        Object response = new Validate().validate(map, btnAdd);

        //Enter key pressed
        if (keyEvent.getCode()== KeyCode.ENTER) {
            if (response instanceof JFXTextField){
                JFXTextField textField= (JFXTextField) response;
                textField.requestFocus();
            }else if (response instanceof Boolean){
                saveSupplier();
            }
        }

    }
}
