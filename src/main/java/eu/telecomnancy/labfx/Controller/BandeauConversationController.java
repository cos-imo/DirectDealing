package eu.telecomnancy.labfx.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ComboBox;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import java.text.SimpleDateFormat;
import java.sql.*;

import eu.telecomnancy.labfx.Connect;

public class BandeauConversationController{

    @FXML
    Label label_event;

    @FXML
    Label label_nom;

    @FXML
    Label label_heure;

    @FXML
    Label label_content;
    
    @FXML
    ComboBox viewSelector;

    private MessagerieController controller;
    private String contactFirstName;
    private String contactLastName;
    private int contactId;
    private int eventId;
    private String eventName;

    public void setElementData(int nom, String contenu, int event, java.sql.Timestamp date) throws SQLException{
        this.eventId = event;
        this.contactId = nom;
        String query1 = "SELECT First_Name, Last_Name FROM User WHERE User_id = ?;";
        String query2 = "SELECT Name FROM Event WHERE Event_id = ?;";
        Connect connect = new Connect();
        try (Connection connection = connect.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query1);
            preparedStatement.setInt(1, nom);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                this.contactFirstName = resultSet.getString("First_Name");
                this.contactLastName = resultSet.getString("Last_Name");
                label_nom.setText(this.contactFirstName + " " + this.contactLastName);
            }
            preparedStatement = connection.prepareStatement(query2);
            preparedStatement.setInt(1, event);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                this.eventName = resultSet.getString("Name");
                label_event.setText(this.eventName);
            }
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM - HH:mm");
        String heureformatee = dateFormat.format(date);
        label_heure.setText(heureformatee);
        label_content.setText(contenu);
    }

    public void setParent(MessagerieController controller){
        this.controller = controller;
    }

    @FXML
    private void openConversation(){
        this.controller.setChat(this.contactFirstName + " " + this.contactLastName, this.eventName, this.contactId, this.eventId);
    }
}