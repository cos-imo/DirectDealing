package eu.telecomnancy.labfx.Controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.concurrent.Worker.State;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class HeaderController extends HBox {
    @FXML
    private Button btnOpenCalendar;
    
    // Le BorderPane racine de la scène principale
    private BorderPane mainRoot;

    private void setMainRoot(BorderPane mainRoot) {
        this.mainRoot = mainRoot;
    }

    @FXML
    private void OpenCalendar(ActionEvent event) {
            BorderPane root = new BorderPane();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/eu/telecomnancy/labfx/fxml/Calendrier.fxml"));

            Scene scene = new Scene(root, 1080, 720);

            // HeaderController header = new HeaderController();
            // root.setTop(header);
            try {
                root.setCenter(loader.load());
            } catch (IOException e) {
                e.printStackTrace();
            }

            Node source = (Node) event.getSource();
            Stage primaryStage = (Stage) source.getScene().getWindow();

            primaryStage.setTitle("TelecomNancy DirectDealing");

            primaryStage.setScene(scene);
            primaryStage.show();
    }

    @FXML
    private void retourAccueil(ActionEvent event){
            BorderPane root = new BorderPane();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/eu/telecomnancy/labfx/fxml/Accueil.fxml"));

            Scene scene = new Scene(root, 1080, 720);

            // HeaderController header = new HeaderController();
            // root.setTop(header);
            try {
                root.setCenter(loader.load());
            } catch (IOException e) {
                e.printStackTrace();
            }

            Node source = (Node) event.getSource();
            Stage primaryStage = (Stage) source.getScene().getWindow();

            primaryStage.setTitle("TelecomNancy DirectDealing");

            primaryStage.setScene(scene);
            primaryStage.show();
    }

    @FXML
    private void afficherMonCompte(ActionEvent event){
            BorderPane root = new BorderPane();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/eu/telecomnancy/labfx/fxml/MonCompte.fxml"));

            Scene scene = new Scene(root, 1080, 720);

            // HeaderController header = new HeaderController();
            // root.setTop(header);
            try {
                root.setCenter(loader.load());
            } catch (IOException e) {
                e.printStackTrace();
            }

            Node source = (Node) event.getSource();
            Stage primaryStage = (Stage) source.getScene().getWindow();

            primaryStage.setTitle("TelecomNancy DirectDealing");

            primaryStage.setScene(scene);
            primaryStage.show();
    }
    
}
