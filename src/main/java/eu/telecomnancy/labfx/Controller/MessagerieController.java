package eu.telecomnancy.labfx.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Label;
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

    @FXML
    private Label contactName;

    @FXML
    private Label eventName;

    public void initialize() throws SQLException {
        getMessages();
    }

    private void addElementToMessageList(int sender, String contenu, int event) throws SQLException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/eu/telecomnancy/labfx/fxml/BandeauConversation.fxml"));
            Node content = loader.load();

            BandeauConversationController objectController = loader.getController();
            objectController.setParent(this);
            objectController.setElementData(sender, contenu, event);

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
                "SELECT e.Event_id, " +
                     "CASE WHEN m.Sender_id < m.Receiver_id THEN m.Sender_id ELSE m.Receiver_id END AS user1_id, " +
                     "CASE WHEN m.Sender_id < m.Receiver_id THEN m.Receiver_id ELSE m.Sender_id END AS user2_id, " +
                     "MAX(m.Date) AS last_message_date, " +
                     "m.Contenu " +
                     "FROM Message m " +
                     "JOIN Event e ON m.Event_lie_id = e.Event_id " +
                     "WHERE m.Sender_id = ? OR m.Receiver_id = ? " +
                     "GROUP BY e.Event_id, user1_id, user2_id " +
                     "ORDER BY e.Event_id ASC"
                );
            preparedStatement.setInt(1, user_id);
            preparedStatement.setInt(2, user_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            int sender = 0;
            int event_id = 0;
            String message = "";
            while (resultSet.next()) {
                sender = resultSet.getInt("user2_id");
                message = resultSet.getString("Contenu");
                event_id = resultSet.getInt("Event_id");
                addElementToMessageList(sender, message, event_id);
                }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void setChat(String nomCorrespondant, String nomEvent){
        contactName.setText(nomCorrespondant);
        eventName.setText(nomEvent);
    }
}
