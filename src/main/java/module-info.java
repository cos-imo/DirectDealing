module eu.telecomnancy.labfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.joda.time;
    
    opens eu.telecomnancy.labfx.Controller to javafx.fxml;
    exports eu.telecomnancy.labfx.Controller;

    opens eu.telecomnancy.labfx to javafx.fxml;
    exports eu.telecomnancy.labfx;
}