package controller;

import model.SupplyOrder;
import model.SupplyOrderDetails;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SupplierOrderCrudController {

    public boolean saveSupplyOrder(SupplyOrder order) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("INSERT INTO supplierorder VALUES (?,?,?,?,?)",order.getId(),order.getDate(),order.getTime(),order.getTotalCost(),order.getSupplierId());
    }

    public boolean saveSupplyOrderDetails(ArrayList<SupplyOrderDetails> details) throws SQLException, ClassNotFoundException {
        for (SupplyOrderDetails det:details) {
            boolean isDetailsSaved = CrudUtil.executeUpdate("INSERT INTO `supplier order detail` VALUES (?,?,?,?)", det.getOrderId(),det.getItemId(),det.getQty(),det.getPrice());
            if (isDetailsSaved){
                if (!updateQty(det.getItemId(),det.getQty())){
                    return false;
                }
            }else{
                return false;
            }
        }
        return true;
    }

    private boolean updateQty(String itemId, int qty) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("UPDATE item SET qtyOnHand=qtyOnHand+? WHERE code=?",qty,itemId);
    }

    public static String getLastId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.executeQuery("SELECT id FROM supplierorder ORDER BY id DESC LIMIT 1");

        if (resultSet.next()){
            return resultSet.getString(1);
        }
        return null;
    }
}
