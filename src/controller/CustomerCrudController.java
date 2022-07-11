package controller;

import model.Customer;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerCrudController {

    // Get Customer from the id
    public static Customer getCustomer(String id) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.executeQuery("SELECT * FROM customer WHERE id=?", id);

        if (resultSet.next()) {
            return new Customer(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4));

        }
        return null;
    }

    // get Customer IDs
    public static ArrayList<String> getIds() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.executeQuery("SELECT id FROM customer");

        ArrayList<String> idList = new ArrayList<>();
        while (resultSet.next()) {
            idList.add(resultSet.getString(1));
        }
        return idList;
    }

    // Get Customer By Name
    public static Customer getCustomerByName(String name) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.executeQuery("SELECT * FROM customer WHERE name = ?", name);

        if (resultSet.next()) {
            return new Customer(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4));
        }
        return null;

    }

    // Save Customer
    public static boolean saveCustomer(Customer c) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("INSERT INTO customer VALUES (?,?,?,?)", c.getId(), c.getName(), c.getAddress(), c.getContact());
    }

    // Get Last Customer ID
    public static String getLastCustomerId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.executeQuery("SELECT id FROM customer ORDER BY id DESC LIMIT 1");

        if (resultSet.next()) {
            return resultSet.getString(1);
        }
        return null;
    }

}
