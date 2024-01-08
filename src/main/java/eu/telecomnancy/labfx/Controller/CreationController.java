package eu.telecomnancy.labfx.Controller;

import java.io.IOException;

import javafx.event.ActionEvent;
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

public class CreationController {
    @FXML
    private TextField prenomField;
    @FXML
    private TextField nomField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField emailConfirmField;
    @FXML
    private PasswordField mdpField;
    @FXML
    private PasswordField mdpConfirmField;
    @FXML
    private Button createAccount;
    @FXML
    private Button goToConnect;
    @FXML
    private Label errorLabel;

    @FXML
    private void setGoToConnectBtn(ActionEvent event){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/eu/telecomnancy/labfx/fxml/Connexion.fxml"));

        Scene scene;
        try {
            scene = new Scene(loader.load(), 1080, 720);
            Node source = (Node) event.getSource();
            Stage primaryStage = (Stage) source.getScene().getWindow();

            primaryStage.setTitle("TelecomNancy DirectDealing");

            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    @FXML
    private void setCreateAccountBtn(){
        // String prenom = prenomField.getText();
        // String nom = nomField.getText();
        // String email = emailField.getText();
        // String emailConfirm = emailConfirmField.getText();
        // String password = mdpField.getText();
        // String passwordConfirm = mdpConfirmField.getText();
        // if (infosValid(prenom, nom, email, emailConfirm, password, passwordConfirm)){
        //     //TODO : create account
        // }
    }
    
    private boolean infosValid(String prenom, String nom, String email, String emailConfirm, String password, String passwordConfirm){
        if (isNameValid(prenom) && isNameValid(nom)) {
            if (isEmailValid(email) && email.equals(emailConfirm)){
                if (isPasswordValid(password) && password.equals(passwordConfirm)){
                    return true;
                }
                else{
                    if (!isPasswordValid(password)){
                        errorLabel.setText("Le mot de passe ne vérifie pas les conditions");
                    } else
                        errorLabel.setText("Les mots de passe ne correspondent pas.");
                    }
                } else {
                    if (!isEmailValid(email)){
                        errorLabel.setText("L'adresse mail ne vérifie pas les conditions");
                    } else
                    errorLabel.setText("Les adresses mail ne correspondent pas.");
                }
            }
            else{
                errorLabel.setText("Le nom et le prénom doivent être composés uniquement de lettres");
            }
        errorLabel.setVisible(true);
        return false;
    }

    private boolean isNameValid(String name){
        return name.matches("[a-zA-Z]+");
    }
    private boolean isEmailValid(String name){
        return name.matches("[a-zA-Z0-9]+.?[a-zA-Z0-9]+@[a-zA-Z]+.?[a-zA-Z]+");
    }
    private boolean isPasswordValid(String name){
        return name.matches("[a-zA-Z0-9]+");
    }
}
