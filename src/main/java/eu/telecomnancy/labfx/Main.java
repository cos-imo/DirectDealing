package eu.telecomnancy.labfx;

import java.io.IOException;

import eu.telecomnancy.labfx.Controller.HeaderController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.application.Platform;

public class Main extends Application {

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {

        BorderPane root = new BorderPane();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/eu/telecomnancy/labfx/fxml/Accueil.fxml"));

        Scene scene = new Scene(loader.load(), 1080, 720);

        HeaderController header = new HeaderController();
        root.setTop(header);

        primaryStage.setTitle("TelecomNancy DirectDealing");

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
