package eu.telecomnancy.labfx.Controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import eu.telecomnancy.labfx.Connect;
import eu.telecomnancy.labfx.Session;
import eu.telecomnancy.labfx.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.IOException;

public class MonCompteController {
    @FXML
    private TextField TextFieldFirstName;

    @FXML
    private TextField TextFieldLastName;

    @FXML
    private TextField TextFieldEmail;

    @FXML
    private Label LabelWallet;

    @FXML
    private Label LabelNote;

    @FXML
    private Button BtnLoadImage;

    @FXML
    private ImageView PhotoProfil;

    @FXML
    private PasswordField TextFieldPassword;

    @FXML
    private PasswordField TextFieldConfirmPassword;

    @FXML
    private Button BtnChangePassword;

    @FXML
    private Label LabelError;

    private int user_id;

    public void initialize() throws SQLException {
        User currentUser = Session.getInstance().getCurrentUser();
        this.user_id = currentUser.getId();
        Connect connect = new Connect();
        Connection connection = connect.getConnection();
        String query = "SELECT * FROM User WHERE User_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, user_id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                TextFieldFirstName.setText(rs.getString("First_Name"));
                TextFieldLastName.setText(rs.getString("Last_Name"));
                TextFieldEmail.setText(rs.getString("Mail"));
                LabelWallet.setText(rs.getString("Wallet"));
                LabelNote.setText(rs.getString("Note"));
                if (rs.getBytes("Photo_profil") != null) {
                    PhotoProfil.setImage(new Image(new ByteArrayInputStream(rs.getBytes("Photo_profil"))));
                }
                pstmt.close();
                connection.commit();
                connection.close();
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void setFirstName(ActionEvent event) {
        resetErrorLabel();
        String firstName = TextFieldFirstName.getText();
        if (firstName.length() > 0) {
            try {
                Connect connect = new Connect();
                Connection connection = connect.getConnection();
                String stmt = "UPDATE User SET First_Name = ? WHERE User_id = ?";
                try (PreparedStatement pstmt = connection.prepareStatement(stmt)) {
                    pstmt.setString(1, firstName);
                    pstmt.setInt(2, user_id);
                    pstmt.executeUpdate();
                    pstmt.close();
                    connection.commit();
                    connection.close();
                    Session.getInstance().setCurrentUser(User.newUserFromId(user_id));
                    setErrorLabel("Prénom changé avec succès.");
                    cleanLabel();
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            setErrorLabel("Veuillez rentrer un prénom valide.");
            cleanLabel();
        }
    }

    @FXML
    private void setLastName(ActionEvent event) {
        resetErrorLabel();
        String lastName = TextFieldLastName.getText();
        if (lastName.length() > 0) {
            try {
                Connect connect = new Connect();
                Connection connection = connect.getConnection();
                String stmt = "UPDATE User SET Last_Name = ? WHERE User_id = ?";
                try (PreparedStatement pstmt = connection.prepareStatement(stmt)) {
                    pstmt.setString(1, lastName);
                    pstmt.setInt(2, user_id);
                    pstmt.executeUpdate();
                    pstmt.close();
                    connection.commit();
                    connection.close();
                    Session.getInstance().setCurrentUser(User.newUserFromId(user_id));
                    setErrorLabel("Nom changé avec succès.");
                    cleanLabel();
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            setErrorLabel("Veuillez rentrer un nom valide.");
            cleanLabel();
        }
    }

    @FXML
    private void setEmail(ActionEvent event) {
        resetErrorLabel();
        String email = TextFieldEmail.getText();
        try {
            Connect connect = new Connect();
            Connection connection = connect.getConnection();
            String query = "SELECT * FROM User WHERE Mail = ?";
            try (PreparedStatement pstmt = connection.prepareStatement(query)) {
                pstmt.setString(1, email);
                ResultSet rs = pstmt.executeQuery();
                if (!rs.next()) {
                    pstmt.close();
                    connection.commit();
                    connection.close();
                    setErrorLabel("Erreur lors du changement d'adresse mail.");
                    cleanLabel();
                }
                else {
                    if (isEmailValid(email)) {
                        String stmt = "UPDATE User SET Mail = ? WHERE User_id = ?";
                        try (PreparedStatement pstmt2 = connection.prepareStatement(stmt)) {
                            pstmt2.setString(1, email);
                            pstmt2.setInt(2, user_id);
                            pstmt2.executeUpdate();
                            pstmt2.close();
                            connection.commit();
                            connection.close();
                            Session.getInstance().setCurrentUser(User.newUserFromId(user_id));
                            setErrorLabel("Adresse mail changée avec succès.");
                            cleanLabel();
                        }
                    }
                    else {
                        pstmt.close();
                        connection.commit();
                        connection.close();
                        setErrorLabel("Veuillez rentrer une adresse mail valide.");
                        cleanLabel();
                    }
                }

            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void setLoadImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image");
        File selectedFile = fileChooser.showOpenDialog(BtnLoadImage.getScene().getWindow());
        if (selectedFile != null) {
            try {
                byte[] imageBytes = Files.readAllBytes(selectedFile.toPath());
                Connect connect = new Connect();
                Connection connection = connect.getConnection();
                String stmt = "UPDATE User SET Photo_profil = ? WHERE User_id = ?";
                try (PreparedStatement pstmt = connection.prepareStatement(stmt)) {
                    pstmt.setBytes(1, imageBytes);
                    pstmt.setInt(2, user_id);
                    pstmt.executeUpdate();
                    PhotoProfil.setImage(new Image(new ByteArrayInputStream(imageBytes)));
                    pstmt.close();
                    connection.commit();
                    connection.close();
                    Session.getInstance().setCurrentUser(User.newUserFromId(user_id));
                    LoadPage.loadPage("MonCompte", event,getClass());
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isEmailValid(String name){
        return name.matches("[a-zA-Z0-9]+.?[a-zA-Z0-9]+@[a-zA-Z]+.?[a-zA-Z]+");
    }

    @FXML
    private void setChangePassword(ActionEvent event) {
        resetErrorLabel();
        if (TextFieldPassword.getText().equals("") || TextFieldConfirmPassword.getText().equals("")) {
            setErrorLabel("Veuillez rentrer un mot de passe.");
            cleanLabel();
        }
        else {
            String password = TextFieldPassword.getText();
            String passwordConfirm = TextFieldConfirmPassword.getText();
            if (password.equals(passwordConfirm)){
                String query = "SELECT Password FROM User WHERE User_id = ?";
                try {
                    Connect connect = new Connect();
                    Connection connection = connect.getConnection();
                    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
                        pstmt.setInt(1, user_id);
                        ResultSet rs = pstmt.executeQuery();
                        if (!rs.next()) {
                            pstmt.close();
                            connection.commit();
                            connection.close();
                            setErrorLabel("Erreur lors du changement de mot de passe.");
                            cleanLabel();
                        }
                        else {
                            if (!rs.getString("Password").equals(User.getHashedPassword(password))) {
                                String stmt = "UPDATE User SET Password = ? WHERE User_id = ?";
                                try(PreparedStatement pstmt2 = connection.prepareStatement(stmt)) {
                                    pstmt2.setString(1, User.getHashedPassword(password));
                                    pstmt2.setInt(2, user_id);
                                    pstmt2.executeUpdate();
                                    pstmt2.close();
                                    connection.commit();
                                    connection.close();
                                    Session.getInstance().setCurrentUser(User.newUserFromId(user_id));
                                    setErrorLabel("Mot de passe changé avec succès.");
                                    cleanLabel();
                                }
                            }
                            else {
                                pstmt.close();
                                connection.commit();
                                connection.close();
                                
                            }
                        }
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else {
                setErrorLabel("Les mots de passe ne correspondent pas.");
                cleanLabel();
            }
        }
    }

    private void resetErrorLabel() {
        LabelError.setText("");
    }

    private void setErrorLabel(String error) {
        LabelError.setText(error);
    }

    private void cleanLabel() {
        TextFieldPassword.setText("");
        TextFieldConfirmPassword.setText("");
        TextFieldFirstName.setText(Session.getInstance().getCurrentUser().getPrenom());
        TextFieldLastName.setText(Session.getInstance().getCurrentUser().getNom());
        TextFieldEmail.setText(Session.getInstance().getCurrentUser().getEmail());
        LabelWallet.setText(String.valueOf(Session.getInstance().getCurrentUser().getWallet().getFlorain()));
        LabelNote.setText(String.valueOf(Session.getInstance().getCurrentUser().getNote()));
        if (Session.getInstance().getCurrentUser().getPdp() != null) {
            PhotoProfil.setImage(Session.getInstance().getCurrentUser().getPdp());
        }
    }
}