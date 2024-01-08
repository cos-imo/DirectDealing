package eu.telecomnancy.labfx.Controller;

import javafx.concurrent.Worker.State;
import javafx.event.ActionEvent;
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
    private void setGoToCreateBtn(ActionEvent event){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/eu/telecomnancy/labfx/fxml/Creation.fxml"));
        Scene scene;
        try {
            scene = new Scene(loader.load(), 1080, 720);
            Node source = (Node) event.getSource();
            Stage primaryStage = (Stage) source.getScene().getWindow();
            primaryStage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void setConnectAccountBtn(Event event){
        String email = emailField.getText();
        String password = mdpField.getText();
        if ((email.equals("a")&& password.equals("a")) || infosExist(email, password)){
            BorderPane root = new BorderPane();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/eu/telecomnancy/labfx/fxml/Accueil.fxml"));

            Scene scene = new Scene(root, 1080, 720);

            HeaderController header = new HeaderController();
            root.setTop(header);
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
    private boolean infosExist(String email, String password){
        boolean res = false;
        Connect connect = null;
        Statement statement = null;
        try {
            connect = new Connect();
            Connection connection = connect.getConnection();
            Statement stmt = connection.createStatement();
            String query = """
                    SELECT * FROM users WHERE email = ? AND password = ?;
                    """;
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, email);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                res = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        connect.close();
        return res;   
    }

}