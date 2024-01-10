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
import eu.telecomnancy.labfx.Session;

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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/eu/telecomnancy/labfx/fxml/BandeauConversation.fxml"));
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
        int user_id = Session.getInstance().getCurrentUser().getId();
        try (Connection connection = connect.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT DISTINCT e.Event_id, m.Contenu, CASE WHEN m.Sender_id < m.Receiver_id THEN m.Sender_id ELSE m.Receiver_id END AS user1_id, CASE WHEN m.Sender_id < m.Receiver_id THEN m.Receiver_id ELSE m.Sender_id END AS user2_id FROM Message m JOIN Event e ON m.Event_lie_id = e.Event_id WHERE m.Sender_id = ? OR m.Receiver_id = ?;"
                );
            preparedStatement.setInt(1, user_id);
            preparedStatement.setInt(2, user_id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String sender = resultSet.getString("user1_id");
                String message = resultSet.getString("Contenu");
                addElementToMessageList(sender, message);
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
