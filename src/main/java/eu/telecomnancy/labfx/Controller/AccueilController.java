package eu.telecomnancy.labfx.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.fxml.FXMLLoader;
import java.io.IOException;

public class AccueilController {

    @FXML
    private Button btnOpenCalendar;
    
    // Le BorderPane racine de la scène principale
    private BorderPane mainRoot;

    public void setMainRoot(BorderPane mainRoot) {
        this.mainRoot = mainRoot;
    }

    @FXML
    private void onOpenCalendar() {
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
