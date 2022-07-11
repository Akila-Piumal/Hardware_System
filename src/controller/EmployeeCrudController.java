package controller;

import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeCrudController {

    // get Last employee id
    public String getLastEmployeeId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.executeQuery("SELECT id FROM user ORDER BY id DESC LIMIT 1");
        if (resultSet.next()){
            return resultSet.getString(1);
        }
        return null;
    }
}
