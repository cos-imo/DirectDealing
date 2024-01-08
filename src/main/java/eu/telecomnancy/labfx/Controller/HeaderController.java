package eu.telecomnancy.labfx.Controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class HeaderController extends HBox {
    @FXML
    public Button btnOpenCalendar;
    
    // Le BorderPane racine de la scène principale
    private BorderPane mainRoot;

    public void setMainRoot(BorderPane mainRoot) {
        this.mainRoot = mainRoot;
    }

    @FXML
    public void OpenCalendar() {
        System.out.println("OpenCalendar enregistré");
        try {
            // Charger le Calendrier.fxml
            FXMLLoader calendrierLoader = new FXMLLoader(getClass().getResource("/eu/telecomnancy/labfx/fxml/Calendrier.fxml"));
            GridPane calendrierContent = calendrierLoader.load();

            // Mettre à jour le contenu central du BorderPane principal
            mainRoot.setCenter(calendrierContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
