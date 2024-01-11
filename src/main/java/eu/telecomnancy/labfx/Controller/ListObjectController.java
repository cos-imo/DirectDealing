package eu.telecomnancy.labfx.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import java.io.ByteArrayInputStream;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import eu.telecomnancy.labfx.Connect;
import eu.telecomnancy.labfx.Session;

import java.text.SimpleDateFormat;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class ListObjectController{

    @FXML
    Label label_nom;

    @FXML
    Label label_type;

    @FXML
    Label label_nomPreteur;

    @FXML
    Label prix_florains;

    @FXML
    ImageView image_annonce;

    @FXML
    Label label_date;

    @FXML
    Button BtnReserver;

    @FXML
    DatePicker DatePickerDebut;

    @FXML
    DatePicker DatePickerFin;

    @FXML
    ComboBox<Integer> hourDebutComboBox;

    @FXML
    ComboBox<Integer> minuteDebutComboBox;

    @FXML
    ComboBox<Integer> hourFinComboBox;

    @FXML
    ComboBox<Integer> minuteFinComboBox;

    private int ressource_id;
    private String annonceName;
    private Boolean type;
    private String desc;
    private String cout;
    private int owner_id;

    private int selectedDebutHour = -1;
    private int selectedDebutMinute = -1;
    private int selectedFinHour = -1;
    private int selectedFinMinute = -1;
    private boolean notEnoughFlorains = true;

    public void setElementData(String annonceName,String desc, Boolean type, String cout, Image image, java.sql.Date dateDebut, java.sql.Date dateFin, int ressource_id, int owner_id){
        
        this.ressource_id = ressource_id;
        this.annonceName = annonceName;
        this.type = type;
        this.desc = desc;
        this.cout = cout;
        this.owner_id = owner_id;

        notEnoughFlorains = Session.getInstance().getCurrentUser().getWallet().getFlorain() < Integer.parseInt(cout);
        BtnReserver.setDisable(notEnoughFlorains);
        DatePickerDebut.setDisable(notEnoughFlorains);
        DatePickerFin.setDisable(notEnoughFlorains);
        hourDebutComboBox.setDisable(notEnoughFlorains);
        minuteDebutComboBox.setDisable(notEnoughFlorains);
        hourFinComboBox.setDisable(notEnoughFlorains);
        minuteFinComboBox.setDisable(notEnoughFlorains);

        label_nom.setText(annonceName);
        label_type.setText("" + type);
        label_nomPreteur.setText(desc);
        prix_florains.setText(cout);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        label_date.setText("Du " + dateFormat.format(dateDebut.getTime()) + " au " + dateFormat.format(dateFin.getTime()));
        if (image_annonce!=null){
            image_annonce.setImage(image);
        }
        for (int i = 0; i < 24; i++) {
            hourDebutComboBox.getItems().add(i);
            hourFinComboBox.getItems().add(i);
        }

        for (int i = 0; i < 60; i++) {
            minuteDebutComboBox.getItems().add(i);
            minuteFinComboBox.getItems().add(i);
        }
    }

    @FXML
    private void setHourDebutComboBox() {
        selectedDebutHour = hourDebutComboBox.getValue();
    }

    @FXML
    private void setMinuteDebutComboBox() {
        selectedDebutMinute = minuteDebutComboBox.getValue();
    }

    @FXML
    private void setHourFinComboBox() {
        selectedFinHour = hourFinComboBox.getValue();
    }

    @FXML
    private void setMinuteFinComboBox() {
        selectedFinMinute = minuteFinComboBox.getValue();
    }

    @FXML
    private void setBtnReserver(ActionEvent event) throws SQLException{
        System.out.println(DatePickerDebut);
        System.out.println(DatePickerFin);
        if (DatePickerDebut == null || DatePickerFin == null || selectedDebutHour == -1 || selectedDebutMinute == -1 || selectedFinHour == -1 || selectedFinMinute == -1){
            System.out.println("Date invalide");
            return;
        }
        else {
            LocalDate dateDebut = DatePickerDebut.getValue();
            LocalDate dateFin = DatePickerFin.getValue();
            if (dateDebut.isAfter(dateFin)) {
                System.out.println("Date de début après date de fin");
                return;
            }
            else {
                Connect connect = new Connect();
                try (Connection connection = connect.getConnection()) {
                    String query = "INSERT INTO Event (Ressource_id, isObjet, Name, preteur_id, acheteur_id, DateDebut, DateFin, Prix) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
                    PreparedStatement preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setInt(1, ressource_id);
                    preparedStatement.setBoolean(2, type);
                    preparedStatement.setString(3, annonceName);
                    preparedStatement.setInt(4, owner_id);
                    preparedStatement.setInt(5, Session.getInstance().getCurrentUser().getId());
                    int minuteDebutI = this.minuteDebutComboBox.getValue();
                    int minuteFinI = this.minuteFinComboBox.getValue();
                    int hourFinI = this.hourFinComboBox.getValue();
                    int hourDebutI = this.hourDebutComboBox.getValue();


                    // Créer les LocalDateTime
                    LocalDateTime dateTimeDebut = LocalDateTime.of(DatePickerDebut.getValue().getYear(), DatePickerDebut.getValue().getMonth(), DatePickerDebut.getValue().getDayOfMonth(), hourDebutI, minuteDebutI);
                    LocalDateTime dateTimeFin = LocalDateTime.of(DatePickerFin.getValue().getYear(), DatePickerFin.getValue().getMonth(), DatePickerFin.getValue().getDayOfMonth(), hourFinI, minuteFinI);



                    // Convertir les LocalDateTime en java.sql.Date mais avec les secondes
                    java.sql.Timestamp sqlTimestampDebut = java.sql.Timestamp.valueOf(dateTimeDebut);
                    java.sql.Timestamp sqlTimestampFin = java.sql.Timestamp.valueOf(dateTimeFin);
                    // System.out.println("Date début (sqlTimestamp): " + sqlTimestampDebut);
                    // System.out.println("Date fin (sqlTimestamp): " + sqlTimestampFin);
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