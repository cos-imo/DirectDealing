module eu.telecomnancy.labfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    exports eu.telecomnancy.labfx.Controller;

    opens eu.telecomnancy.labfx to javafx.fxml;
    exports eu.telecomnancy.labfx;
}