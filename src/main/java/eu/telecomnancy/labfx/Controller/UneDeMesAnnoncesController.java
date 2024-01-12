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


    public void setElementData(Image image,String annonceName, Boolean type,  java.sql.Date dateDebut, java.sql.Date dateFin, String cout,Boolean reccurence){
   

        label_nom.setText(annonceName);
        label_type.setText(type ? "Objet" : "Service");
        prix_florains.setText(cout);
        label_Recurence.setText(reccurence ? "Oui" : "Non");   
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date dateDebutAnnonce = dateDebut;
        Date dateFinAnnonce = dateFin;
        label_date.setText("Du " + dateFormat.format(dateDebutAnnonce.getTime()) + " au " + dateFormat.format(dateFinAnnonce.getTime()));
        if (image_annonce!=null){
            image_annonce.setImage(image);
        }
    }

}