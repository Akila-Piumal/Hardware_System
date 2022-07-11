package controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import util.CrudUtil;

import java.io.IOException;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class DailyIncomeFormController {
    public BarChart<Date, Double>  incomeChart;
    public NumberAxis y;
    public CategoryAxis x;
    public Label lblTodayIncome;

    public void initialize() {
        getAllDate();

        setTodayIncome();

    }

    // Set Today Income to the LAbel
    private void setTodayIncome() {
        try {
            double orderIncome=0;
            double rentIncome=0;

            ResultSet result = CrudUtil.executeQuery("SELECT SUM(`order`.cost) FROM `order`WHERE date=?", LocalDate.now());
            if (result.next()){
                orderIncome=result.getDouble(1);
            }

            ResultSet result2 = CrudUtil.executeQuery("SELECT SUM(rent.cost) FROM rent WHERE date=?", LocalDate.now());
            if (result2.next()){
                rentIncome=result2.getDouble(1);
            }

            lblTodayIncome.setText(String.valueOf(orderIncome+rentIncome));

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Set income details to the Chart
    private void getAllDate() {
        //  Create Serious for Selling income
        XYChart.Series<Date, Double> series1 = new XYChart.Series<>();
        series1.setName("Selling");

        // Create Serious for Rent income
        XYChart.Series<Date, Double> series2 = new XYChart.Series<>();
        series2.setName("Rent");

        for (int i = 0; i < 10; i++) {
            try {
                // get all selling income of the day
                ResultSet resultSet = CrudUtil.executeQuery("SELECT SUM(`order`.cost) FROM `order`WHERE date=?", LocalDate.now().minusDays(i));
                double sellingIncome = 0;
                if (resultSet.next()) {
                    sellingIncome = resultSet.getDouble(1);
                }

                // Add to the serious
                series1.getData().add(new XYChart.Data(LocalDate.now().minusDays(i) + "", sellingIncome));

                // get all Rent income of the day
                ResultSet resultSet2 = CrudUtil.executeQuery("SELECT SUM(rent.cost) FROM rent WHERE date=?", LocalDate.now().minusDays(i));
                double rentIncome = 0;
                if (resultSet2.next()) {
                    rentIncome = resultSet2.getDouble(1);
                }

                // Add to the serious
                series2.getData().add(new XYChart.Data(LocalDate.now().minusDays(i) + "", rentIncome));

            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        // insert all serious in to the chart
        incomeChart.getData().addAll(series1, series2);
    }
}
