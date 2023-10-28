module com.example.dam_distributeur_boissons_javafx {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens com.example.dam_distributeur_boissons_javafx to javafx.fxml;
    exports com.example.dam_distributeur_boissons_javafx;
}