package eu.telecomnancy.labfx;

import java.io.IOException;

import eu.telecomnancy.labfx.Controller.AccueilController;
import eu.telecomnancy.labfx.Controller.HeaderController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class Main extends Application {

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {

        BorderPane root = new BorderPane();



        FXMLLoader accueilLoader = new FXMLLoader(getClass().getResource("/eu/telecomnancy/labfx/fxml/Accueil.fxml"));
        BorderPane accueilContent = accueilLoader.load();


        AccueilController accueilController = accueilLoader.getController();

        

        HeaderController header = (HeaderController) accueilLoader.getController();
        root.setTop(header);
        
        accueilController.setMainRoot(root);

        root.setCenter(accueilContent);


        Scene scene = new Scene(root, 400, 400);
        primaryStage.setTitle("TelecomNancy DirectDealing");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
