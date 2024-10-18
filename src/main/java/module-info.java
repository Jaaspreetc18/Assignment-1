module com.example.data_choice {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.data_choice to javafx.fxml;
    exports com.example.data_choice;
}