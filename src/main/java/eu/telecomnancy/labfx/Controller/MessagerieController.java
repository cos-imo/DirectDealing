package eu.telecomnancy.labfx.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import java.util.List;
import java.lang.Object;
import eu.telecomnancy.labfx.Connect;
import java.io.IOException;
import java.sql.*;

public class MessagerieController {

    @FXML
    private VBox messageListContainer;

    public void initialize() throws SQLException {
        getMessages();
    }
    
    private void addElementToMessageList(String sender, String contenu) throws SQLException{
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/eu/telecomnancy/labfx/fxml/Conversation.fxml"));
            Node content = loader.load();

            ConversationController objectController = loader.getController();

            objectController.setElementData(sender, contenu);

            messageListContainer.getChildren().addAll(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int getMessages() throws SQLException{

        Connect connect = new Connect();
        try (Connection connection = connect.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Message;");
            ResultSet resultSet = preparedStatement.executeQuery();

             while (resultSet.next()) {

                String sender = resultSet.getString(3);
                String message = resultSet.getString(5);
                addElementToMessageList(sender, message);
            }
            resultSet.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

}