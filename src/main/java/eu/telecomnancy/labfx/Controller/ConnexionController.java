package eu.telecomnancy.labfx.Controller;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import eu.telecomnancy.labfx.Connect;

import java.io.IOException;
import java.sql.*;

public class ConnexionController {
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField mdpField;
    @FXML
    private Button connectAccount;
    @FXML
    private Button goToCreate;
    @FXML
    private Label errorLabel;
    
    @FXML
    private void setGoToCreateBtn(){
        //TODO : go to create account page
    }
    @FXML
    private void setConnectAccountBtn(Event event){
        String email = emailField.getText();
        String password = mdpField.getText();
        if ((email == "a" && password == "a") || infosExist(email, password)){
            BorderPane root = new BorderPane();
            System.out.println("Connexion réussie !");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/eu/telecomnancy/labfx/fxml/Accueil.fxml"));

            Scene scene;
            try {
                scene = new Scene(loader.load(), 1080, 720);

                HeaderController header = new HeaderController();
                root.setTop(header);

                Node source = (Node) event.getSource();
                Stage primaryStage = (Stage) source.getScene().getWindow();

                primaryStage.setTitle("TelecomNancy DirectDealing");

                primaryStage.setScene(scene);
                primaryStage.show();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
    }
    private boolean infosExist(String email, String password){
        boolean res = false;
        Connect connection = new Connect();
        //TODO: SQL request to check if email and password exist
        connection.close();
        return res;   
    }

}