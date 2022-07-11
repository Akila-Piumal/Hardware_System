package controller;

import model.Item;
import model.ReturnOrder;
import model.ReturnOrderDetails;
import util.CrudUtil;
import view.TM.ReturnItemDetailsTM;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ReturnCrudController {

    public static String getLastReturnOrderId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.executeQuery("SELECT id FROM returnorder ORDER BY id DESC LIMIT 1");
        if (resultSet.next()) {
            return resultSet.getString(1);
        }
        return null;
    }

    public boolean saveReturnOrder(ReturnOrder returnOrder) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("INSERT INTO returnorder VALUES (?,?,?)", returnOrder.getId(), returnOrder.getDate(), returnOrder.getCost());
    }

    public boolean saveReturnOrderDetails(ArrayList<ReturnOrderDetails> details) throws SQLException, ClassNotFoundException {
        for (ReturnOrderDetails det : details) {
            if (CrudUtil.executeUpdate("INSERT INTO returnorderdetail VALUES (?,?,?,?)", det.getReturnId(), det.getItemCode(), det.getQty(), det.getPrice())) {
                if (!updateQty(det.getItemCode(), det.getQty())) {
                    return false;
                }
            } else {
                return false;
            }
        }
        return true;
    }

    private boolean updateQty(String itemCode, int qty) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("UPDATE item SET qtyOnHand=qtyOnHand-? WHERE code=?", qty, itemCode);
    }

    public ArrayList<ReturnItemDetailsTM> getReturnItemDetails(String returnId) throws SQLException, ClassNotFoundException {
        ArrayList<ReturnItemDetailsTM> details = new ArrayList<>();
        ResultSet resultSet = CrudUtil.executeQuery("SELECT * FROM returnorderdetail WHERE returnId=?", returnId);
        while (resultSet.next()) {
            String itemCode = resultSet.getString(2);
            Item item = ItemCrudController.getItem(itemCode);

            details.add(new ReturnItemDetailsTM(itemCode, item.getDescription(), item.getBuyingPrice(), resultSet.getInt(3), resultSet.getDouble(4)));
        }
        return details;
    }
}
