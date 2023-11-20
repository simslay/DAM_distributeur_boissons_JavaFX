module com.example.dam_distributeur_boissons_javafx {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    requires org.json;

    opens com.example.dam_distributeur_boissons_javafx to javafx.fxml;
    exports com.example.dam_distributeur_boissons_javafx;
    exports com.example.dam_distributeur_boissons_javafx.ui;
    opens com.example.dam_distributeur_boissons_javafx.ui to javafx.fxml;
    exports com.example.dam_distributeur_boissons_javafx.model;
    opens com.example.dam_distributeur_boissons_javafx.model to javafx.fxml;
}