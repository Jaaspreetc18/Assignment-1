package com.example.data_choice;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class DataController {
    @FXML
    private BarChart<String, Number> barChart;
    @FXML
    private RadioButton mathButton;
    @FXML
    private RadioButton scienceButton;
    @FXML
    private RadioButton historyButton;
    @FXML
    private RadioButton englishButton;
    @FXML
    private Button tableButton;
    @FXML
    private Button chartButton;
    @FXML
    private TableView<DataModel> tableView;
    @FXML
    private TableColumn<DataModel, String> studentNameColumn;
    @FXML
    private TableColumn<DataModel, String> subjectColumn;
    @FXML
    private TableColumn<DataModel, String> gradeColumn;
    @FXML
    private TableColumn<DataModel, String> semesterColumn;

    private ToggleGroup subjectGroup;
    private DatabaseConnector dbConnector;

    @FXML
    private void initialize() {
        dbConnector = new DatabaseConnector();
        setupToggleGroup();
        setupTableColumns();
        mathButton.setSelected(true);
        populateTableView();
    }

    private void setupToggleGroup() {
        subjectGroup = new ToggleGroup();
        mathButton.setToggleGroup(subjectGroup);
        scienceButton.setToggleGroup(subjectGroup);
        historyButton.setToggleGroup(subjectGroup);
        englishButton.setToggleGroup(subjectGroup);

        subjectGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> updateChart());
    }

    private void setupTableColumns() {
        studentNameColumn.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        subjectColumn.setCellValueFactory(new PropertyValueFactory<>("subject"));
        gradeColumn.setCellValueFactory(new PropertyValueFactory<>("grade"));
        semesterColumn.setCellValueFactory(new PropertyValueFactory<>("semester"));
    }

    private void updateChart() {
        barChart.getData().clear();
        XYChart.Series<String, Number> gradeSeries = new XYChart.Series<>();

        String selectedSubject = ((RadioButton) subjectGroup.getSelectedToggle()).getText();

        gradeSeries.setName(selectedSubject);

        try (Connection conn = dbConnector.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM student_performance WHERE subject = '" + selectedSubject + "'")) {

            while (rs.next()) {
                String semester = rs.getString("semester");
                double grade = rs.getDouble("grade");

                gradeSeries.getData().add(new XYChart.Data<>(semester, grade));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        barChart.getData().add(gradeSeries);
    }

    private void populateTableView() {
        tableView.getItems().clear();
        try (Connection conn = dbConnector.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM student_performance")) {

            while (rs.next()) {
                tableView.getItems().add(new DataModel(
                        rs.getString("student_name"),
                        rs.getString("subject"),
                        rs.getDouble("grade"),
                        rs.getString("semester")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void switchToTableView() {
        barChart.setVisible(false);
        tableButton.setVisible(false);
        tableView.setVisible(true);
        chartButton.setVisible(true);
    }

    @FXML
    private void switchToChartView() {
        barChart.setVisible(true);
        tableButton.setVisible(true);
        tableView.setVisible(false);
        chartButton.setVisible(false);
    }
}
