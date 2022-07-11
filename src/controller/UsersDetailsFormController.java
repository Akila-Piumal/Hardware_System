package controller;

import com.jfoenix.controls.JFXTextField;
import model.User;

public class UsersDetailsFormController {
    public JFXTextField txtNic;
    public JFXTextField txtName;
    public JFXTextField txtAddress;
    public JFXTextField txtContact;
    public JFXTextField txtSalary;

    public static User user;

    public void initialize(){

        txtNic.setText(user.getNic());
        txtName.setText(user.getName());
        txtAddress.setText(user.getAddress());
        txtContact.setText(user.getContact());
        txtSalary.setText(String.valueOf(user.getSalary()));

    }
}
