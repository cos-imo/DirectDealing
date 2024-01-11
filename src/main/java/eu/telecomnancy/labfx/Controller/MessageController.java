package eu.telecomnancy.labfx.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import java.text.SimpleDateFormat;
import java.sql.*;

public class MessageController {
    @FXML
    private Label messageBody;

    @FXML
    private Label messageHeure;

    @FXML
    protected void setMessage(String messageContent, java.sql.Timestamp messageHorodatage){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM - HH:mm");
        String heureformatee = dateFormat.format(messageHorodatage);
        messageBody.setText(messageContent);
        messageHeure.setText(heureformatee);
    }
}
