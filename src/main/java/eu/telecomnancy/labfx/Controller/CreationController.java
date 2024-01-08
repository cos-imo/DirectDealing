package eu.telecomnancy.labfx.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

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
    private PasswordField passwordField;
    @FXML
    private PasswordField passwordConfirmField;
    @FXML
    private Button createAccount;
    @FXML
    private Button goToConnect;
    @FXML
    private Label errorLabel;

    @FXML
    private void setCreateAccountBtn(){
        String prenom = prenomField.getText();
        String nom = nomField.getText();
        String email = emailField.getText();
        String emailConfirm = emailConfirmField.getText();
        String password = passwordField.getText();
        String passwordConfirm = passwordConfirmField.getText();
        if (infosValid(prenom, nom, email, emailConfirm, password, passwordConfirm)){
            //TODO : create account
        }
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
