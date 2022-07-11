package controller;

import model.User;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserCrudController {
    public static User getUser(String id) throws SQLException, ClassNotFoundException {

        ResultSet resultSet = CrudUtil.executeQuery("SELECT * FROM user WHERE id=?", id);
        if (resultSet.next()){
            return new User(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),resultSet.getString(4),resultSet.getString(5),resultSet.getDouble(6),resultSet.getString(7));

        }
        return null;
    }
}
