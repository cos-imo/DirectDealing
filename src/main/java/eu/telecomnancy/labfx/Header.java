package eu.telecomnancy.labfx;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class Header extends HBox {
    public Header() {
        Label titleLabel = new Label("Ici le header");
        getChildren().add(titleLabel);
    }
}
