package eu.telecomnancy.labfx.Controller;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class HeaderController extends HBox {
    public HeaderController() {
        Label titleLabel = new Label("Ici le header");
        getChildren().add(titleLabel);
    }
}
