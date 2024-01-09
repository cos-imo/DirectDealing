package eu.telecomnancy.labfx.Controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public final class LoadPage {

    public LoadPage() {}

    public static void loadPage(String PageName, ActionEvent event,Class class1){
    BorderPane root = new BorderPane();
        FXMLLoader loader = new FXMLLoader(class1.getResource("/eu/telecomnancy/labfx/fxml/" + PageName + ".fxml"));

        Scene scene = new Scene(root, 1080, 720);

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
