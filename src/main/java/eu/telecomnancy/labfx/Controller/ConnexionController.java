package eu.telecomnancy.labfx.Controller;

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
import eu.telecomnancy.labfx.Session;
import eu.telecomnancy.labfx.User;

import java.io.IOException;
import java.sql.*;

// import com.google.common.hash.Hashing;

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
        if (infosExist(email, password)){
            BorderPane root = new BorderPane();
            
            Scene scene = new Scene(root, 1080, 720);
            
            // HeaderController header = new HeaderController();
            // root.setTop(header);
            try {
                User currentUser = User.newUserFromMail(email);
                if (currentUser == null){
                    System.out.println("Erreur lors de la récupération de l'utilisateur");
                }
                Session.getInstance().setCurrentUser(currentUser);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/eu/telecomnancy/labfx/fxml/Accueil.fxml"));
                root.setCenter(loader.load());
            } catch (IOException | SQLException e) {
                e.printStackTrace();
            }

            Node source = (Node) event.getSource();
            Stage primaryStage = (Stage) source.getScene().getWindow();

            primaryStage.setTitle("TelecomNancy DirectDealing");

            primaryStage.setScene(scene);
            primaryStage.show();

        }
        else{
            setErrorLabel("Email ou mot de passe incorrect");
            cleanLabel();
        }
    }
    private boolean infosExist(String email, String password){
        boolean res = false;
        Connect connect = null;
        Statement statement = null;
        String pass = User.getHashedPassword(password);
        // final String hashed = Hashing.sha256().hashString("your input", StandardCharsets.UTF_8).toString();
        try {
            connect = new Connect();
            Connection connection = connect.getConnection();
            statement = connection.createStatement();
            String query = """
                    SELECT * FROM User WHERE Mail = ? AND Password = ?;
                    """;
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, email);
            pstmt.setString(2, pass);

            ResultSet rs = pstmt.executeQuery();
            if (!rs.isClosed()){ //Se ferme si la requête est vide
                pstmt.close();
                connection.close();
                return true;
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;   
    }

    private void setErrorLabel(String error) {
        errorLabel.setText(error);
    }

    private void cleanLabel() {
        emailField.setText("");
        mdpField.setText("");
    }
}