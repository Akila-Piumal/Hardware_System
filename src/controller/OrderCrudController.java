package controller;

import model.Order;
import model.OrderDetails;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderCrudController {

    public boolean saveOrder(Order order) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("INSERT INTO `order` VALUES (?,?,?,?,?)",order.getId(),order.getDate(),order.getTime(),order.getTotalCost(),order.getCustomerId());
    }

    public boolean saveOrderDetails(ArrayList<OrderDetails> details) throws SQLException, ClassNotFoundException {
        for (OrderDetails details1:details) {
            boolean isDetailsSaved = CrudUtil.executeUpdate("INSERT INTO `order detail` VALUES (?,?,?,?)", details1.getOrderId(), details1.getItemCode(), details1.getQty(), details1.getPrice());
            if (isDetailsSaved){
                if(!updateQty(details1.getItemCode(),details1.getQty())){
                    return false;
                }
            }else{
                return false;
            }
        }
        return true;
    }

    private boolean updateQty(String itemCode, int qty) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("UPDATE item SET qtyOnHand=qtyOnHand-? WHERE code=?",qty,itemCode);
    }

    public static String getLastOrderId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.executeQuery("SELECT id FROM `order` ORDER BY id DESC LIMIT 1");


        if (resultSet.next()){
            return resultSet.getString(1);
        }
        return "D001";
    }
}
