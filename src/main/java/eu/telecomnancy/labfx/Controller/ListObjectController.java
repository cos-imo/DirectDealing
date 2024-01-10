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
    DatePicker datePickerDebut;

    @FXML
    DatePicker datePickerFin;

    @FXML
    private ComboBox<Integer> hourDebutComboBox;

    @FXML
    private ComboBox<Integer> minuteDebutComboBox;

    @FXML
    private ComboBox<Integer> hourFinComboBox;

    @FXML
    private ComboBox<Integer> minuteFinComboBox;

    private int selectedHour = -1;
    private int selectedMinute = -1;
    private boolean notEnoughFlorains = true;

    public void setElementData(String annonceName, String type, String preteur, String cout, Image image, java.sql.Date dateDebut, java.sql.Date dateFin){
        notEnoughFlorains = Session.getInstance().getCurrentUser().getWallet().getFlorain() < Integer.parseInt(cout);
        BtnReserver.setDisable(notEnoughFlorains);
        label_nom.setText(annonceName);
        label_type.setText(type);
        label_nomPreteur.setText(preteur);
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
        selectedHour = hourDebutComboBox.getValue();
    }

    @FXML
    private void setMinuteDebutComboBox() {
        selectedMinute = minuteDebutComboBox.getValue();
    }

    @FXML
    private void setHourFinComboBox() {
        selectedHour = hourFinComboBox.getValue();
    }

    @FXML
    private void setMinuteFinComboBox() {
        selectedMinute = minuteFinComboBox.getValue();
    }

    @FXML
    private void setBtnReserver(ActionEvent event) throws SQLException{
        if (datePickerDebut.getValue() == null || datePickerFin.getValue() == null || selectedHour == -1 || selectedMinute == -1){
            return;
        }
        else {
            LocalDate dateDebut = datePickerDebut.getValue();
            LocalDate dateFin = datePickerFin.getValue();
            if (dateDebut.isAfter(dateFin)) {
                return;
            }
            else {

            }
        }
    }

}