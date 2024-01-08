package eu.telecomnancy.labfx;

import java.io.IOException;

import eu.telecomnancy.labfx.Controller.HeaderController;
import javafx.application.Application;
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

        HeaderController header = new HeaderController();
        root.setTop(header);

        primaryStage.setTitle("TelecomNancy DirectDealing");

        Scene scene = new Scene(root, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
