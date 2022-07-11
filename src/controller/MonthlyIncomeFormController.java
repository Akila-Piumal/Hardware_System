package controller;

import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import util.CrudUtil;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class MonthlyIncomeFormController {
    public BarChart<String,Double> incomeChart;
    public CategoryAxis x;
    public NumberAxis y;

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
            ResultSet resultSet = CrudUtil.executeQuery("select date_format(date,'%M'),sum(`Order`.cost) from `order` group by year(date),month(date) order by year(date),month(date);");
            while (resultSet.next()){
                series1.getData().add(new XYChart.Data(resultSet.getString(1),resultSet.getDouble(2)));
            }

            ResultSet resultSet1 = CrudUtil.executeQuery("select date_format(date,'%M'),sum(rent.cost) from rent group by year(date),month(date) order by year(date),month(date);");
            while (resultSet1.next()){
                series2.getData().add(new XYChart.Data(resultSet1.getString(1),resultSet1.getDouble(2)));
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
