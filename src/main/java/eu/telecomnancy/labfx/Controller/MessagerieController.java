package eu.telecomnancy.labfx.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import eu.telecomnancy.labfx.Connect;

public class MessagerieController {

    @FXML
    private VBox messageListContainer;

    @FXML
    private ScrollPane scrollPane;

    public void initialize() throws SQLException {
        getMessages();
    }

    private void addElementToMessageList(String sender, String contenu) throws SQLException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/eu/telecomnancy/labfx/fxml/Conversation.fxml"));
            Node content = loader.load();

            ConversationController objectController = loader.getController();
            objectController.setElementData(sender, contenu);

            messageListContainer.getChildren().addAll(content);

            // Mettre à jour la position de la barre de défilement
            scrollPane.setVvalue(1.0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getMessages() throws SQLException {
        Connect connect = new Connect();
        try (Connection connection = connect.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Message WHERE ;");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String sender = resultSet.getString(3);
                String message = resultSet.getString(5);
                addElementToMessageList(sender, message);
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
