module com.lozumi.namsgui {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.lozumi.namsgui to javafx.fxml;
    exports com.lozumi.namsgui;
}