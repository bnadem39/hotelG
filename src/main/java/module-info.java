module com.hotel.main {
    requires javafx.controls;
    requires javafx.fxml;

    exports com.hotel.main;
    exports com.hotel.model;
    exports com.hotel.controller;

    opens com.hotel.controller to javafx.fxml;
    opens com.hotel.model to javafx.base;
}
