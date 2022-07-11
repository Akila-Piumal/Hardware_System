package controller;

import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AnnuallyIncomeFormController {
    public BarChart<String, Double> incomeChart;
    public NumberAxis y;
    public CategoryAxis x;

    public void initialize() {
        getAllDate();

    }

    private void getAllDate() {
        //  Create Serious for Selling income
        XYChart.Series<String, Double> series1 = new XYChart.Series<>();
        series1.setName("Selling");

        // Create Serious for Rent income
        XYChart.Series<String, Double> series2 = new XYChart.Series<>();
        series2.setName("Rent");

        try {

            // Get order income of months
            ResultSet resultSet = CrudUtil.executeQuery("select year(date ),sum(`Order`.cost) from `order` group by year(date) order by year(date);");
            while (resultSet.next()) {
                series1.getData().add(new XYChart.Data(resultSet.getString(1), resultSet.getDouble(2)));

            }

            // Get rent income of months
            ResultSet resultSet1 = CrudUtil.executeQuery("select year(date ),sum(Rent.cost) from rent group by year(date) order by year(date);");
            while (resultSet1.next()) {
                series2.getData().add(new XYChart.Data(resultSet1.getString(1), resultSet1.getDouble(2)));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        // insert all serious in to the chart
        incomeChart.getData().addAll(series1, series2);
    }
}
