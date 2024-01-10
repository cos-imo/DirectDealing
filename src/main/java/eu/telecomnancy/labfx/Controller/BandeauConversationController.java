package eu.telecomnancy.labfx.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ComboBox;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import java.sql.*;

import eu.telecomnancy.labfx.Connect;

public class BandeauConversationController{

    @FXML
    Label label_event;

    @FXML
    Label label_nom;

    @FXML
    Label label_content;
    
    @FXML
    ComboBox viewSelector;

    public void setElementData(int nom, String contenu, int event) throws SQLException{
        String query1 = "SELECT First_Name, Last_Name FROM User WHERE User_id = ?;";
        String query2 = "SELECT Name FROM Event WHERE Event_id = ?;";
        Connect connect = new Connect();
        try (Connection connection = connect.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query1);
            preparedStatement.setInt(1, nom);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                label_nom.setText(resultSet.getString("First_Name") + " " + resultSet.getString("Last_Name"));
            }
            preparedStatement = connection.prepareStatement(query2);
            preparedStatement.setInt(1, event);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                label_event.setText(resultSet.getString("Name"));
            }
        }
        label_content.setText(contenu);
    }

    @FXML
    private void openConversation(){
        System.out.println("Conversation ouverte");
    }
}