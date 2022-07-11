package controller;

import model.Customer;
import model.Supplier;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SupplierCrudController {
    public static ArrayList<String> getSupplierIds() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.executeQuery("SELECT id FROM supplier");

        ArrayList<String> idList=new ArrayList<>();

        while (resultSet.next()){
            idList.add(resultSet.getString(1));
        }
        return idList;
    }

    public static Supplier getSupplier(String id) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.executeQuery("SELECT * FROM supplier WHERE id=?", id);

        if (resultSet.next()){
            return new Supplier(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),resultSet.getString(4),resultSet.getString(5));
        }
        return null;
    }

    public static boolean saveSupplier(Supplier s) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("INSERT INTO supplier VALUES (?,?,?,?,?)",s.getId(),s.getName(),s.getAddress(),s.getContact(),s.getCompany());
    }

    public static String getLastSupplierId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.executeQuery("SELECT id FROM supplier ORDER BY id DESC LIMIT 1");

        if (resultSet.next()){
            return resultSet.getString(1);
        }
        return null;
    }

    public static Supplier getCustomerByName(String name) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.executeQuery("SELECT * FROM supplier WHERE name = ?", name);

        if (resultSet.next()){
            return new Supplier(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),resultSet.getString(4),resultSet.getString(5));
        }
        return null;

    }
}
