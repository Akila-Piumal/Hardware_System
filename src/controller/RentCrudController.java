package controller;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import model.Item;
import model.Rent;
import model.RentDetails;
import util.CrudUtil;
import view.TM.RentItemDetailsTM;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RentCrudController {

    public static String getLastRentId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.executeQuery("SELECT id FROM rent ORDER BY id DESC LIMIT 1");

        if (resultSet.next()) {
            return resultSet.getString(1);
        }
        return "R001";
    }


    public boolean saveRent(Rent rent) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("INSERT INTO rent VALUES (?,?,?,?,?)", rent.getId(), rent.getDate(), rent.getTime(), rent.getTotalCost(), rent.getCustomerId());
    }


    public boolean saveRentDetails(ArrayList<RentDetails> details) throws SQLException, ClassNotFoundException {
        for (RentDetails det : details) {
            boolean isDetailsSaved = CrudUtil.executeUpdate("INSERT INTO `rent detail` VALUES (?,?,?,?,?,?,?)", det.getRentId(), det.getItemCode(), det.getQty(), det.getRentDays(), det.getReturnDate(), det.getPrice(), det.getStatus());
            if (isDetailsSaved) {
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


    public ArrayList<RentItemDetailsTM> getRentItemDetails(String rentId) throws SQLException, ClassNotFoundException {

        ArrayList<RentItemDetailsTM> details = new ArrayList<>();
        ResultSet resultSet = CrudUtil.executeQuery("SELECT * FROM `rent detail` WHERE rentId=?", rentId);

        while (resultSet.next()) {
            String itemCode = resultSet.getString(2);
            Item item = ItemCrudController.getItem(itemCode);

            HBox box=new HBox();

            details.add(new RentItemDetailsTM(itemCode, item.getDescription(), item.getSellingPrice(), resultSet.getInt(3), resultSet.getInt(4), resultSet.getString(5), resultSet.getDouble(6),box));

        }
        return details;
    }
}
