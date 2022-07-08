module com.grh.grh {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires validatorfx;
    requires eu.hansolo.tilesfx;

    opens com.grh.grh to javafx.fxml;
    exports com.grh.grh;
}