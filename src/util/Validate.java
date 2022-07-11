package util;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class Validate {
    // Validate textFields
    public Object validate(LinkedHashMap<JFXTextField, Pattern> map , JFXButton btn) {

        for (JFXTextField textField : map.keySet()) {
            Pattern pattern = map.get(textField);

            if (!pattern.matcher(textField.getText()).matches()) {
                // not matched
                addError(textField , btn);
                return textField;
            }

            removeError(textField , btn);
        }
        return true;
    }

    // Remove error of the textField
    private void removeError(JFXTextField txtField , JFXButton btn) {
        txtField.setStyle("-fx-border-color: green");
        btn.setDisable(false);
    }

    // Add error to the textField
    private void addError(JFXTextField txtField , JFXButton btn) {
        if (txtField.getText().length()>0){
            txtField.setStyle("-fx-border-color: red");
        }

        btn.setDisable(true);
    }



}
