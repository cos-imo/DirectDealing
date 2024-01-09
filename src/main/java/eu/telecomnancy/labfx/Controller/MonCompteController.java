package eu.telecomnancy.labfx.Controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
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
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class MonCompteController {
    @FXML
    Label LabelFirstName;

    @FXML
    Label LabelLastName;

    @FXML
    Label LabelEmail;

    @FXML
    Label LabelWallet;

    @FXML
    Label LabelNote;

    @FXML
    Button BtnLoadImage;

    @FXML
    ImageView PhotoProfil;

    private int user_id;

    public void initialize() throws SQLException{
        User currentUser = Session.getInstance().getCurrentUser();
        this.user_id = currentUser.getId();
        Connect connect = new Connect();
        Connection connection = connect.getConnection();
        String query = "SELECT * FROM User WHERE User_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, user_id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                LabelFirstName.setText(rs.getString("First_Name"));
                LabelLastName.setText(rs.getString("Last_Name"));
                LabelEmail.setText(rs.getString("Mail"));
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
}
