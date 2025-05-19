module ejemplo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.graphics;
    //requires transitive javafx.graphics;
    opens ejemplo to javafx.fxml;
    exports ejemplo;
}
