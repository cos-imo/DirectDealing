package eu.telecomnancy.labfx.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import java.sql.*;

public class ConversationController{

    @FXML
    Label label_nom;

    @FXML
    Label label_content;

    public void setElementData(String nom, String sender){
        label_nom.setText(nom);
        label_content.setText(sender);
    }
}