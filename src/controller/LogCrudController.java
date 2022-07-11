package controller;

import model.Log;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LogCrudController {
    public static Log getLoggingDetails(String userId) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.executeQuery("SELECT * FROM log WHERE id=?", userId);
        if (resultSet.next()){
            return new Log(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3));

        }
        return null;
    }
}
