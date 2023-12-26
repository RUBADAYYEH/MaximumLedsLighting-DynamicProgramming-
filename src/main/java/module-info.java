module com.example.maxledlightingproject {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.maxledlightingproject to javafx.fxml;
    exports com.example.maxledlightingproject;
}