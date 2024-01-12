package eu.telecomnancy.labfx.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.text.SimpleDateFormat;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.lang.Object;
import javafx.scene.control.DatePicker;
import eu.telecomnancy.labfx.Connect;
import eu.telecomnancy.labfx.EventRessource;
import eu.telecomnancy.labfx.Recurrence;
import eu.telecomnancy.labfx.Ressource;
import eu.telecomnancy.labfx.Session;
import javafx.scene.control.TextField;
import javafx.scene.control.ChoiceBox;
import java.io.IOException;
import java.sql.*;

public class MesAnnoncesController {

    @FXML
    private VBox MesAnnoncesContainer;
    
    @FXML
    private ChoiceBox<String> type;

    @FXML
    private ChoiceBox<String> ressourceevent;

    public void initialize() throws SQLException {
        type.setValue("Service et Objet");
        ressourceevent.setValue("Ressource");
        type.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                updateElements();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        ressourceevent.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                updateElements();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        getElements();
    }

    private void updateElements() throws SQLException {
        // Effacer le contenu actuel
        MesAnnoncesContainer.getChildren().clear();
    
        // Mettre à jour le contenu
        getElements();
    }

    
    private void addElementToSearchList(String name, Boolean type, int Prix, Image image ,DateTime dateDebut, DateTime dateFin, Recurrence recurrence) throws SQLException{
        System.out.println(name);
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/eu/telecomnancy/labfx/fxml/UneDeMesAnnonces.fxml"));
            Node content = loader.load();

            UneDeMesAnnoncesController uneDeMesAnnoncesController = loader.getController();

            uneDeMesAnnoncesController.setElementData(name, type, Prix, image, dateDebut, dateFin, recurrence);

            MesAnnoncesContainer.getChildren().addAll(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

 
    private int getElements() throws SQLException{

        ArrayList<Ressource> mesRessources = Session.getInstance().getCurrentUser().getRessources();
        ArrayList<EventRessource> mesEvents = Session.getInstance().getCurrentUser().getEventRessource();
        System.out.println(mesRessources);
        System.out.println(mesEvents);
        int ressource_id;
        String name;
        int Prix;
        Boolean type;
        DateTime dateDebut;
        DateTime dateFin;
        Image image;
        Recurrence recurrence;
        String type_recherche = this.type.getValue();
        if (ressourceevent.getValue().equals("Ressource")) {
            for (Ressource ressource : mesRessources) {
                System.out.println(ressource);
                if (type_recherche.equals("Service et Objet")) {
                    ressource_id = ressource.getId();
                    name = ressource.getName();
                    Prix = ressource.getPrix().getFlorain();
                    type = ressource.getType();
                    dateDebut = ressource.getDateDebut();
                    dateFin = ressource.getDateFin();
                    image = ressource.getPdp();
                    recurrence = ressource.getReccurence();
                    addElementToSearchList(name, type, Prix, image, dateDebut, dateFin, recurrence);
                }
                else if (type_recherche.equals("Service") && ressource.getType() == false) {
                    ressource_id = ressource.getId();
                    name = ressource.getName();
                    Prix = ressource.getPrix().getFlorain();
                    type = ressource.getType();
                    dateDebut = ressource.getDateDebut();
                    dateFin = ressource.getDateFin();
                    image = ressource.getPdp();
                    recurrence = ressource.getReccurence();
                    addElementToSearchList(name, type, Prix, image, dateDebut, dateFin, recurrence);
                }
                else if (type_recherche.equals("Objet") && ressource.getType() == true) {
                    ressource_id = ressource.getId();
                    name = ressource.getName();
                    Prix = ressource.getPrix().getFlorain();
                    type = ressource.getType();
                    dateDebut = ressource.getDateDebut();
                    dateFin = ressource.getDateFin();
                    image = ressource.getPdp();
                    recurrence = ressource.getReccurence();
                    addElementToSearchList(name, type, Prix, image, dateDebut, dateFin, recurrence);
                }
            }
        }
        else {
            for (EventRessource event : mesEvents) {
                if (type_recherche.equals("Service et Objet")) {
                    ressource_id = event.getRessource().getId();
                    name = event.getRessource().getName();
                    Prix = event.getRessource().getPrix().getFlorain();
                    type = event.getRessource().getType();
                    dateDebut = event.getDateDebut();
                    dateFin = event.getDateFin();
                    image = event.getRessource().getPdp();
                    recurrence = event.getRessource().getReccurence();
                    addElementToSearchList(name, type, Prix, image, dateDebut, dateFin, recurrence);
                }
                else if (type_recherche.equals("Service") && event.getRessource().getType() == false) {
                    ressource_id = event.getRessource().getId();
                    name = event.getRessource().getName();
                    Prix = event.getRessource().getPrix().getFlorain();
                    type = event.getRessource().getType();
                    dateDebut = event.getDateDebut();
                    dateFin = event.getDateFin();
                    image = event.getRessource().getPdp();
                    recurrence = event.getRessource().getReccurence();
                    addElementToSearchList(name, type, Prix, image, dateDebut, dateFin, recurrence);
                }
                else if (type_recherche.equals("Objet") && event.getRessource().getType() == true) {
                    ressource_id = event.getRessource().getId();
                    name = event.getRessource().getName();
                    Prix = event.getRessource().getPrix().getFlorain();
                    type = event.getRessource().getType();
                    dateDebut = event.getDateDebut();
                    dateFin = event.getDateFin();
                    image = event.getRessource().getPdp();
                    recurrence = event.getRessource().getReccurence();
                    addElementToSearchList(name, type, Prix, image, dateDebut, dateFin, recurrence);
                }
            }   
        }
    return 0;
    }
}
    
