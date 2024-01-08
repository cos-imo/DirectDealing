package eu.telecomnancy.labfx;

import java.io.IOException;
import java.sql.SQLException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {


        FXMLLoader loader = new FXMLLoader(getClass().getResource("/eu/telecomnancy/labfx/fxml/Connexion.fxml"));

        Scene scene = new Scene(loader.load(), 1080, 720);

        primaryStage.setTitle("TelecomNancy DirectDealing");

        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
