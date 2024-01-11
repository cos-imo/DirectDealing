package eu.telecomnancy.labfx.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;

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

    @FXML
    private VBox messagesContainer;

    @FXML
    private Button boutonEnvoyer;

    @FXML
    private TextField messageSaisi;

    private boolean isConversationInitialized;
    private String nomCorrespondant;
    private String nomEvent;
    private int correspondantActif;
    private int eventActif;

    public void initialize() throws SQLException {
        getMessages();
    }

    private void addElementToMessageList(int sender, String contenu, int event, java.sql.Timestamp date) throws SQLException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/eu/telecomnancy/labfx/fxml/BandeauConversation.fxml"));
            Node content = loader.load();

            BandeauConversationController objectController = loader.getController();
            objectController.setParent(this);
            objectController.setElementData(sender, contenu, event, date);

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
                     "ORDER BY m.Date DESC"
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
                java.sql.Timestamp heure = resultSet.getTimestamp("last_message_date");
                addElementToMessageList(sender, message, event_id, heure);
                }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void setChat(String nomCorrespondant, String nomEvent, int contactId, int eventId){
        this.nomCorrespondant = nomCorrespondant;
        this.nomEvent = nomEvent;
        this.correspondantActif = contactId;
        this.eventActif = eventId;
        messagesContainer.getChildren().clear();

        contactName.setText(nomCorrespondant);
        eventName.setText(nomEvent);

        if (!isConversationInitialized){
            initConversation();
        }

        Connect connect = new Connect();
        int user_id = Session.getInstance().getCurrentUser().getId();
        try (Connection connection = connect.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(
                """
                SELECT *
                FROM Message
                WHERE Event_lie_id = ?
                AND (Sender_id = ? OR Receiver_id = ?);
                """
            );
            preparedStatement.setInt(1, eventId);
            preparedStatement.setInt(2, contactId);
            preparedStatement.setInt(2, contactId);
            ResultSet resultSet = preparedStatement.executeQuery();
            int sender = 0;
            String message = "";
            while (resultSet.next()) {
                //id1 = resultSet.getInt("user2_id");
                message = resultSet.getString("Contenu");
                java.sql.Timestamp date = resultSet.getTimestamp("Date");
                addMessage(message, date);
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void initConversation(){

    }

    @FXML
    private boolean envoyerMessage(){
        String messageAEnvoyer = messageSaisi.getText();
        int user_id = Session.getInstance().getCurrentUser().getId();

        Connect connect = new Connect();
        try (Connection connection = connect.getConnection()) {
            String sql = "INSERT INTO Message (Event_lie_id, Sender_id, Receiver_id, Date, Contenu) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, this.eventActif);
            preparedStatement.setInt(2, user_id);
            preparedStatement.setInt(3, this.correspondantActif);
            preparedStatement.setDate(4, java.sql.Date.valueOf(LocalDate.now()));
            preparedStatement.setString(5, messageSaisi.getText());

            // Requête d'insertion
            int rowsAffected = preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.commit();
            connection.close();
            messageSaisi.clear();
            this.setChat(this.nomCorrespondant, this.nomEvent, this.correspondantActif, this.eventActif);
            // Retourner vrai si une ligne a été insérée, faux sinon
            return rowsAffected > 0;
            }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void addMessage(String messageContent, java.sql.Timestamp messageDate){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/eu/telecomnancy/labfx/fxml/Message.fxml"));
            Node content = loader.load();

            MessageController msgController = loader.getController();

            msgController.setMessage(messageContent, messageDate);

            messagesContainer.getChildren().addAll(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
