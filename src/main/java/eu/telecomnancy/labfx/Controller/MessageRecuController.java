package eu.telecomnancy.labfx.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

import java.text.SimpleDateFormat;

import eu.telecomnancy.labfx.Connect;

import java.io.ByteArrayInputStream;
import java.sql.*;

public class MessageRecuController {
    @FXML
    private Label messageRecuBody;

    @FXML
    private Label messageRecuHeure;

    @FXML
    private ImageView Imageotherid;
    
    @FXML
    protected void setMessage(String messageContent, java.sql.Timestamp messageHorodatage, int sender){
        Rectangle clip = new Rectangle(
            Imageotherid.getFitWidth(), Imageotherid.getFitHeight()
        );
        clip.setArcWidth(80);
        clip.setArcHeight(80);
        Imageotherid.setClip(clip);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM - HH:mm");
        String heureformatee = dateFormat.format(messageHorodatage);
        messageRecuBody.setText(messageContent);
        messageRecuHeure.setText(heureformatee);
        System.out.println(sender);
        String query = "SELECT Photo_profil FROM User WHERE User_id = ?;";
        Connect connect = new Connect();
        try (Connection connection = connect.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, sender);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                if (resultSet.getBytes("Photo_profil") != null) {
                    Imageotherid.setImage(new Image(new ByteArrayInputStream(resultSet.getBytes("Photo_profil"))));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
