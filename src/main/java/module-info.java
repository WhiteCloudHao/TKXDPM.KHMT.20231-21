module com.example.aims_project2 {
    requires java.sql;
    requires javafx.controls;
    requires javafx.fxml;
    opens com.example.aims_project2 to javafx.fxml;
    opens com.example.aims_project2.views.screen to javafx.fxml;
    opens com.example.aims_project2.views.screen.home to javafx.fxml;
    exports com.example.aims_project2;
    exports com.example.aims_project2.views.screen;
    exports com.example.aims_project2.views.screen.home;
}