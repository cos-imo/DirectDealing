package eu.telecomnancy.labfx.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.concurrent.RejectedExecutionHandler;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Duration;
import org.joda.time.Interval;

import eu.telecomnancy.labfx.Connect;
import eu.telecomnancy.labfx.Recurrence;
import eu.telecomnancy.labfx.Session;

public class MessagerieController {

    @FXML
    private VBox messageListContainer;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private Label contactName;

    @FXML
    private Label RessourceName;

    @FXML
    private Label label_desc;

    @FXML
    private VBox messagesContainer;

    @FXML
    private Button boutonEnvoyer;

    @FXML
    private TextField messageSaisi;

    @FXML
    private Button BtnReserver;

    @FXML
    private DatePicker DatePickerDebut;

    @FXML
    private DatePicker DatePickerFin;

    @FXML
    private ComboBox<String> hourDebutComboBox;

    @FXML
    private ComboBox<Integer> minuteDebutComboBox;

    @FXML
    private ComboBox<String> hourFinComboBox;

    @FXML
    private ComboBox<Integer> minuteFinComboBox;

    private int selectedDebutHour = -1;
    private int selectedDebutMinute = -1;
    private int selectedFinHour = -1;
    private int selectedFinMinute = -1;

    private boolean isConversationInitialized;
    private String nomCorrespondant;
    private String nomRessource;
    private int correspondantActif;
    private int RessourceActif;
    private String cout;
    private int owner_id;
    private Image image;
    private Boolean type;
    private Timestamp dateDebutAnnonce;
    private Timestamp dateFinAnnonce;

    public void initialize() throws SQLException {
        for (int i = 0; i <= 24; i++) {
            String hoursNumber = String.valueOf(i) + "H";
            hourDebutComboBox.getItems().add(hoursNumber);
            hourFinComboBox.getItems().add(hoursNumber);
        }

        for (int i = 0; i < 12; i++) {
            minuteDebutComboBox.getItems().add(i*5);
            minuteFinComboBox.getItems().add(i*5);
        }
        getMessages();
        int nb_discussion = messageListContainer.getChildren().size();
        if (nb_discussion > 0) {
            Node firstDiscussion = messageListContainer.getChildren().get(0);
            BandeauConversationController controller = (BandeauConversationController) firstDiscussion.getUserData(); //getUserData() fonctionne grâce à TAG=999999 (dans ce fichier)
            controller.openConversation();
        }
    }

    //public void setElementData(String annonceName, String desc, Boolean type, String cout, Image image ,java.sql.Date dateDebut, java.sql.Date dateFin, int ressource_id, int owner_id) throws SQLException{
    //    this.ressource_id = ressource_id;
    //    this.nomRessource = annonceName;
    //    this.desc = desc;
    //    this.cout = cout;
    //    this.owner_id = owner_id;
    //    this.image = image;
    //    this.dateDebutAnnonce = dateDebut;
    //    this.dateFinAnnonce = dateFin;
    //    this.type = type;
    //    Label_desc.setText(desc);
    //    RessourceName.setText(annonceName);
    //    BtnReserver.setDisable(Session.getInstance().getCurrentUser().getId() == owner_id);
    //    isConversationInitialized = false;
    //}

    @FXML
    private void setHourDebutComboBox() {
        selectedDebutHour = Integer.parseInt(hourDebutComboBox.getValue().substring(0, hourDebutComboBox.getValue().length()-1));
    }

    @FXML
    private void setMinuteDebutComboBox() {
        selectedDebutMinute = minuteDebutComboBox.getValue();
    }

    @FXML
    private void setHourFinComboBox() {
        selectedFinHour = Integer.parseInt(hourFinComboBox.getValue().substring(0, hourFinComboBox.getValue().length()-1));
    }

    @FXML
    private void setMinuteFinComboBox() {
        selectedFinMinute = minuteFinComboBox.getValue();
    }

    @FXML
    private void setBtnReserver(ActionEvent event) throws SQLException{
        System.out.println(DatePickerDebut.getValue());
        System.out.println(DatePickerFin.getValue());
        if (DatePickerDebut.getValue() == null || DatePickerFin.getValue() == null || selectedDebutHour == -1 || selectedDebutMinute == -1 || selectedFinHour == -1 || selectedFinMinute == -1){
            System.out.println("Date invalide");
            return;
        }
        else {

            // Créer les LocalDateTime
            LocalDateTime dateDebut = LocalDateTime.of(DatePickerDebut.getValue().getYear(), DatePickerDebut.getValue().getMonth(), DatePickerDebut.getValue().getDayOfMonth(), selectedDebutHour, selectedDebutMinute);
            LocalDateTime dateFin = LocalDateTime.of(DatePickerFin.getValue().getYear(), DatePickerFin.getValue().getMonth(), DatePickerFin.getValue().getDayOfMonth(), selectedFinHour,selectedFinMinute );
            // Interval interval = new Interval(dateDebut, dateFin);
            Timestamp dateDebutTimestamp = Timestamp.valueOf(dateDebut);
            Timestamp dateFinTimestamp = Timestamp.valueOf(dateFin);
            if (dateDebut.isAfter(dateFin)) {
                System.out.println("Date de début après date de fin");
                return;
            }
            else {
                Recurrence rec = Recurrence.Non;
                Connect connect = new Connect();
                try (Connection connection = connect.getConnection()){
                    PreparedStatement preparedStatement = connection.prepareStatement("SELECT DateDebut, DateFin,type,Name,Owner_id,Prix,Recurrence FROM Ressource WHERE Ressource_id = ?");
                    preparedStatement.setInt(1, RessourceActif);
                    ResultSet resultSet = preparedStatement.executeQuery();
                    if (resultSet.next()) {
                        dateDebutAnnonce = new Timestamp(resultSet.getLong("DateDebut"));
                        dateFinAnnonce = new Timestamp(resultSet.getLong("DateFin"));
                        type = resultSet.getBoolean("type");
                        nomRessource = resultSet.getString("Name");
                        owner_id = resultSet.getInt("Owner_id");
                        cout = resultSet.getString("Prix");
                        rec = Recurrence.getRecurrence(resultSet.getInt("Recurrence"));
                    }
                    resultSet.close();
                    preparedStatement.close();
                    connection.close();
                }
                DateTime newDateDebut = new DateTime(dateDebutTimestamp);
                DateTime newDateFin = new DateTime(dateFinTimestamp); 
                Interval intervalNewDates = new Interval(newDateDebut,newDateFin);
                Interval intervalAnnonce = new Interval(new DateTime(dateDebutAnnonce),new DateTime(dateFinAnnonce));
                if (!isInOccurence(intervalNewDates, intervalAnnonce,rec)) {
                    System.out.println("Date de début ou de fin en dehors de la période de l'annonce");
                    return;
                }
                else {                    
                    connect = new Connect();
                    try (Connection connection = connect.getConnection()) {
                        String query = "INSERT INTO Event (Ressource_id, isObjet, Name, preteur_id, acheteur_id, DateDebut, DateFin, Prix) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
                        PreparedStatement preparedStatement = connection.prepareStatement(query);
                        preparedStatement.setInt(1, RessourceActif);
                        preparedStatement.setBoolean(2, type);
                        preparedStatement.setString(3, nomRessource);
                        preparedStatement.setInt(4, owner_id);
                        preparedStatement.setInt(5, Session.getInstance().getCurrentUser().getId());

                        // Convertir les LocalDateTime en java.sql.Date mais avec les secondes
                        java.sql.Timestamp sqlTimestampDebut = java.sql.Timestamp.valueOf(dateDebut);
                        java.sql.Timestamp sqlTimestampFin = java.sql.Timestamp.valueOf(dateFin);
                        preparedStatement.setTimestamp(6, sqlTimestampDebut);
                        preparedStatement.setTimestamp(7, sqlTimestampFin);

                        preparedStatement.setInt(8, Integer.parseInt(cout));
                        preparedStatement.executeUpdate();
                        query = "UPDATE User SET Wallet = Wallet - ? WHERE User_id = ?;";
                        preparedStatement = connection.prepareStatement(query);
                        preparedStatement.setInt(1, Integer.parseInt(cout));
                        preparedStatement.setInt(2, Session.getInstance().getCurrentUser().getId());
                        preparedStatement.executeUpdate();
                        query = "UPDATE User SET Wallet = Wallet + ? WHERE User_id = ?;";
                        preparedStatement = connection.prepareStatement(query);
                        preparedStatement.setInt(1, Integer.parseInt(cout));
                        preparedStatement.setInt(2, owner_id);
                        preparedStatement.executeUpdate();
                        System.out.println("Réservation effectuée");
                        Session.getInstance().getCurrentUser().getWallet().addFlorain(-Integer.parseInt(cout));
                        preparedStatement.close();
                        connection.commit();
                        connection.close();
                    }
                }
            }
        }
    }
    private boolean isInOccurence(Interval intervalNewDates, Interval intervalAnnonce, Recurrence rec) {
        System.out.println("Début intervalle new : "+intervalNewDates.getStart());
        System.out.println(intervalNewDates.getEnd());
        System.out.println(intervalAnnonce.getStart());
        System.out.println("Début intervalle annonce : "+intervalAnnonce.getEnd());
        DateTime newDateDebut = new DateTime(intervalAnnonce.getStart());
        DateTime newDateFin = new DateTime(intervalAnnonce.getEnd());
        Interval newInterval = intervalNewDates;
        while (newDateDebut.isBefore(intervalNewDates.getEnd())) {
            if (newInterval.contains(intervalNewDates)) {
                return true;
            }
            switch (rec) {
                case Non:
                    return newInterval.contains(intervalNewDates);
                case Quotidien:
                    newDateDebut = newDateDebut.plusDays(1);
                    newDateFin = newDateFin.plusDays(1);
                    break;
                case Hebdomadaire:
                    newDateDebut = newDateDebut.plusMonths(1);
                    newDateFin = newDateFin.plusMonths(1);
                    break;
                case Mensuel:
                    newDateDebut = newDateDebut.plusMonths(1);
                    newDateFin = newDateFin.plusMonths(1);
                    break;
                default:
                    break;
            }
            newInterval = new Interval(newDateDebut,newDateFin);
        }
        return false;

    }

    private void addElementToMessageList(int sender, String contenu, int ressource, java.sql.Timestamp date) throws SQLException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/eu/telecomnancy/labfx/fxml/BandeauConversation.fxml"));
            Node content = loader.load();

            BandeauConversationController objectController = loader.getController();
            objectController.setParent(this);
            objectController.setElementData(sender, contenu, ressource, date);
            content.setUserData(objectController); //setUserData() fonctionne grâce à TAG=999999 (dans ce fichier)
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
                "SELECT r.Desc, r.Ressource_id, " +
                     "CASE WHEN m.Sender_id < m.Receiver_id THEN m.Sender_id ELSE m.Receiver_id END AS user1_id, " +
                     "CASE WHEN m.Sender_id < m.Receiver_id THEN m.Receiver_id ELSE m.Sender_id END AS user2_id, " +
                     "MAX(m.Date) AS last_message_date, " +
                     "m.Contenu " +
                     "FROM Message m " +
                     "JOIN Ressource r ON m.Ressource_liee_id = r.Ressource_id " +
                     "WHERE m.Sender_id = ? OR m.Receiver_id = ? " +
                     "GROUP BY r.Ressource_id, user1_id, user2_id " +
                     "ORDER BY m.Date DESC"
                );
            preparedStatement.setInt(1, user_id);
            preparedStatement.setInt(2, user_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            int sender = 0;
            int ressource_id = 0;
            String message = "";
            while (resultSet.next()) {
                sender = resultSet.getInt("user2_id");
                message = resultSet.getString("Contenu");
                ressource_id = resultSet.getInt("Ressource_id");
                java.sql.Timestamp heure = resultSet.getTimestamp("last_message_date");
                label_desc.setText(resultSet.getString("Desc"));
                addElementToMessageList(sender, message, ressource_id, heure);
                }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void setChat(String nomCorrespondant, String nomRessource, int contactId, int ressource_id){
        this.nomCorrespondant = nomCorrespondant;
        System.out.println(nomRessource);
        this.nomRessource = nomRessource;
        System.out.println(this.nomRessource);
        this.correspondantActif = contactId;
        this.RessourceActif = ressource_id;
        messagesContainer.getChildren().clear();

        contactName.setText(nomCorrespondant);
        RessourceName.setText(this.nomRessource);

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
                WHERE Ressource_liee_id = ?
                AND (Sender_id = ? OR Receiver_id = ?);
                """
            );
            preparedStatement.setInt(1, ressource_id);
            preparedStatement.setInt(2, contactId);
            preparedStatement.setInt(3, contactId);
            ResultSet resultSet = preparedStatement.executeQuery();
            String message = "";
            int sender = 0;
            while (resultSet.next()) {
                sender = resultSet.getInt("Sender_id");
                //id1 = resultSet.getInt("user2_id");
                message = resultSet.getString("Contenu");
                java.sql.Timestamp date = resultSet.getTimestamp("Date");
                addMessage(message, date, sender);
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
            String sql = "INSERT INTO Message (Ressource_liee_id, Sender_id, Receiver_id, Date, Contenu) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, this.RessourceActif);
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
            this.setChat(this.nomCorrespondant, this.nomRessource, this.correspondantActif, this.RessourceActif);
            // Retourner vrai si une ligne a été insérée, faux sinon
            return rowsAffected > 0;
            }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void addMessage(String messageContent, java.sql.Timestamp messageDate, int sender){
        try {
            FXMLLoader loader;
            Node content;
            if (sender == Session.getInstance().getCurrentUser().getId()) {
                loader = new FXMLLoader(getClass().getResource("/eu/telecomnancy/labfx/fxml/MessageEnvoi.fxml"));
                content = loader.load();

                MessageEnvoiController msgenvoiController = loader.getController();
                msgenvoiController.setMessage(messageContent, messageDate, sender);
            }
            else {
                loader = new FXMLLoader(getClass().getResource("/eu/telecomnancy/labfx/fxml/MessageRecu.fxml"));
                content = loader.load();

                MessageRecuController msgrecuController = loader.getController();
                msgrecuController.setMessage(messageContent, messageDate, sender);
            }
            
            


            messagesContainer.getChildren().addAll(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
