module com.example.rezeptmedienverwaltung {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    requires com.dlsc.formsfx;

    opens com.example.rezeptverwaltung to javafx.fxml;
    exports com.example.rezeptverwaltung;
}