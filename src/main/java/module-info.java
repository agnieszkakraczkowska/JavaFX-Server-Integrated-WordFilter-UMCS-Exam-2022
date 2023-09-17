module com.example.kolokwium_ii_2022_2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.kolokwium_ii_2022_2 to javafx.fxml;
    exports com.example.kolokwium_ii_2022_2;
}