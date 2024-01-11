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
    Label prix_florains;

    @FXML
    ImageView image_annonce;

    @FXML
    Label label_date;


    private int ressource_id;
    private String annonceName;
    private Boolean type;
    private String desc;
    private String cout;
    private int owner_id;
    private Date dateDebutAnnonce;
    private Date dateFinAnnonce;

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

    

        label_nom.setText(annonceName);
        label_type.setText(type ? "Objet" : "Service");
        label_nomPreteur.setText(desc);
        prix_florains.setText(cout);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateDebutAnnonce = dateDebut;
        dateFinAnnonce = dateFin;
        label_date.setText("Du " + dateFormat.format(dateDebutAnnonce.getTime()) + " au " + dateFormat.format(dateFinAnnonce.getTime()));
        if (image_annonce!=null){
            image_annonce.setImage(image);
        }
    }

}