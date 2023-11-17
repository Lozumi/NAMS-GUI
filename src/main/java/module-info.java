module com.lozumi.namsgui {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.lozumi.namsgui to javafx.fxml;
    //opens com.lozumi.namsgui.view to javafx.fxml;
    //opens com.lozumi.namsgui.model to javafx.fxml;
    exports com.lozumi.namsgui;
}