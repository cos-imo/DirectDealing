package eu.telecomnancy.labfx.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import eu.telecomnancy.labfx.Recurrence;
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