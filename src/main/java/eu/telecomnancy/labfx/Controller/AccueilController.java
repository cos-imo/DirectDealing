package eu.telecomnancy.labfx.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import java.io.IOException;

public class AccueilController {

    @FXML
    private void OpenProposer(ActionEvent event){
            BorderPane root = new BorderPane();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/eu/telecomnancy/labfx/fxml/AjoutItem.fxml"));

            Scene scene = new Scene(root, 1080, 720);

            // HeaderController header = new HeaderController();
            // root.setTop(header);

            try {
                root.setTop(loader.load());
            } catch (IOException e) {
                e.printStackTrace();
            }

            Node source = (Node) event.getSource();
            Stage primaryStage = (Stage) source.getScene().getWindow();

            primaryStage.setTitle("TelecomNancy DirectDealing");

            primaryStage.setScene(scene);
            primaryStage.show();

    }

    public void Recherche(ActionEvent event){
            BorderPane root = new BorderPane();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/eu/telecomnancy/labfx/fxml/Recherche.fxml"));

            Scene scene = new Scene(root, 1080, 720);

            // HeaderController header = new HeaderController();
            // root.setTop(header);

            // addElementToSearchList("@../images/ARG1.jpeg", "ARG2", "ARG3", "ARG4", "ARG5", "ARG6");

            try {
                root.setTop(loader.load());
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