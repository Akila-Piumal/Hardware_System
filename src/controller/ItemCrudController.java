package controller;

import model.Item;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemCrudController {
    public static ArrayList<String> getCodes() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.executeQuery("SELECT code FROM item");

        ArrayList<String> codeList=new ArrayList<>();
        while (resultSet.next()){
            codeList.add(resultSet.getString(1));
        }
        return codeList;
    }

    public static Item getItem(String code) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.executeQuery("SELECT * FROM item WHERE code=?", code);
        if (resultSet.next()){
            return new Item(resultSet.getString(1),resultSet.getString(2),resultSet.getDouble(3),resultSet.getDouble(4),resultSet.getInt(5),resultSet.getString(6));

        }
        return null;
    }

    public static ArrayList<String> getSellingItemCodes(String sellOrRent) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.executeQuery("SELECT code FROM item WHERE status=?", sellOrRent);

        ArrayList<String> codeList=new ArrayList<>();
        while (resultSet.next()){
            codeList.add(resultSet.getString(1));
        }
        return codeList;
    }

    public static String getLastItemCode() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.executeQuery("SELECT code FROM item ORDER BY code DESC LIMIT 1");

        if (resultSet.next()){
            return resultSet.getString(1);
        }
        return null;
    }

    public static boolean saveItem(Item item) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("INSERT INTO item VALUES (?,?,?,?,?,?)",item.getCode(),item.getDescription(),item.getBuyingPrice(),item.getSellingPrice(),0,item.getStatus());
    }
}
