package eu.telecomnancy.labfx.Controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import eu.telecomnancy.labfx.Connect;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

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

    public void initialize(int user_id) throws SQLException{
        this.user_id = user_id;
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
    private void setLoadImage() {
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
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
