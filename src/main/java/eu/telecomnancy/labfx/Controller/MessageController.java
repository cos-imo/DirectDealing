package eu.telecomnancy.labfx.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;

public class MessageController {
    @FXML
    private Label messageBody;

    @FXML
    private Label messageHeure;

    @FXML
    protected void setMessage(String messageContent, String messageHorodatage){
        messageBody.setText(messageContent);
        messageHeure.setText(messageHorodatage);
    }
}
