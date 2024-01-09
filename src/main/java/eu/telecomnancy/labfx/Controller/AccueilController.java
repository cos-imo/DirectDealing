package eu.telecomnancy.labfx.Controller;

import javafx.concurrent.Worker.State;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import eu.telecomnancy.labfx.Connect;
import java.io.IOException;

public class AccueilController {

    @FXML
    public void OpenProposer(ActionEvent event){
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
    
    public void setMainRoot(BorderPane root){
        
    }

}