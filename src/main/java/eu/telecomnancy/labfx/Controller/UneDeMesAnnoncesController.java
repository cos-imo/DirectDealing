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
import eu.telecomnancy.labfx.Recurrence;
import eu.telecomnancy.labfx.Session;

import java.text.SimpleDateFormat;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.joda.time.DateTime;

public class UneDeMesAnnoncesController{

    @FXML
    Label label_nom;

    @FXML
    Label label_type;

    @FXML
    Label label_nomPreteur;

    @FXML
    Label label_nomAcheteur;

    @FXML
    Label label_date;

    @FXML
    Label label_Recurence;

    @FXML
    Label prix_florains;

    @FXML
    ImageView image_annonce;


    public void setElementData(String annonceName, Boolean type, int Prix, Image image, DateTime dateDebut, DateTime dateFin, Recurrence reccurence){
        System.out.println(annonceName);
        //____Image____
        if (image_annonce!=null){
            image_annonce.setImage(image);
        }
        System.out.println(("1"));

        //____Nom____
        label_nom.setText(annonceName);
        System.out.println(("2"));
        //____Type____
        label_type.setText(type ? "Objet" : "Service");
        System.out.println(("3"));

        //____Date____
        DateTime dateDebutAnnonce = dateDebut;
        DateTime dateFinAnnonce = dateFin;
        label_date.setText("Du " + dateDebutAnnonce.getYear() + "-" + dateDebutAnnonce.getMonthOfYear() + "-" + dateDebutAnnonce.getDayOfMonth() + " " + dateDebutAnnonce.getHourOfDay() + ":" + dateDebutAnnonce.getMinuteOfHour() + ":" + dateDebutAnnonce.getSecondOfMinute() + " au " + dateFinAnnonce.getYear() + "-" + dateFinAnnonce.getMonthOfYear() + "-" + dateFinAnnonce.getDayOfMonth() + " " + dateFinAnnonce.getHourOfDay() + ":" + dateFinAnnonce.getMinuteOfHour() + ":" + dateFinAnnonce.getSecondOfMinute());
        System.out.println(("4"));

        //____Prix_____
        prix_florains.setText(Prix+"");
        System.out.println(("5"));

        //____Recurrence_____
        label_Recurence.setText(reccurence.toString());   
        System.out.println(("on est ici"));
    }

}