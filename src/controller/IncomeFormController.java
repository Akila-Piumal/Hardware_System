package controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import util.CrudUtil;

import java.io.IOException;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class IncomeFormController {
    public BarChart<Date, Double> incomeChart;
    public CategoryAxis x;
    public NumberAxis y;
    public AnchorPane dashBoardContext;
    public AnchorPane chartContext;
    public JFXButton btnDailyIncome;

    public void initialize() {
        try {
            Parent parent=FXMLLoader.load(getClass().getResource("../view/DailyIncomeForm.fxml"));
            chartContext.getChildren().clear();
            chartContext.getChildren().add(parent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void btnHomeOnAction(ActionEvent actionEvent) throws IOException {
        dashBoardContext.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("../view/HomeForm.fxml"));
        dashBoardContext.getChildren().add(parent);
    }

    public void dailyIncomeOnAction(ActionEvent actionEvent) {

        try {
            chartContext.getChildren().clear();
            Parent parent = FXMLLoader.load(getClass().getResource("../view/DailyIncomeForm.fxml"));
            chartContext.getChildren().add(parent);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void monthlyIncomeOnAction(ActionEvent actionEvent) {

        try {
            chartContext.getChildren().clear();
            Parent parent = FXMLLoader.load(getClass().getResource("../view/MonthlyIncomeForm.fxml"));
            chartContext.getChildren().add(parent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void annuallyIncomeOnAction(ActionEvent actionEvent) {
        try {
            chartContext.getChildren().clear();
            Parent parent = FXMLLoader.load(getClass().getResource("../view/AnnuallyIncomeForm.fxml"));
            chartContext.getChildren().add(parent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
