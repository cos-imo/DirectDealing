package eu.telecomnancy.labfx.Controller;


import java.sql.Array;
import java.sql.SQLException;
import java.util.ArrayList;

import org.joda.time.DateTime;
import org.joda.time.Interval;


import eu.telecomnancy.labfx.Calendrier;
import eu.telecomnancy.labfx.EventRessource;
import eu.telecomnancy.labfx.ModeAffichage;
import eu.telecomnancy.labfx.Ressource;
import eu.telecomnancy.labfx.Session;
import eu.telecomnancy.labfx.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.event.ActionEvent;

public class HeaderController extends HBox {
    @FXML
    private Button btnOpenCalendar;
    @FXML
    private Button btnOpenMesAnnonces;
    @FXML 
    private Label labelNames;
    @FXML
    private ImageView imageViewPdp;
    @FXML
    private Label labelFlorain;
    @FXML
    private Button disconnectBtn;

    public void initialize() {
        User currentUser = Session.getInstance().getCurrentUser();
        labelNames.setText(currentUser.getPrenom() + " " + currentUser.getNom());
        if (currentUser.getPdp() != null) {
            imageViewPdp.setImage(currentUser.getPdp());
            imageViewPdp.setFitWidth(30);
            imageViewPdp.setFitHeight(30);
        }
        labelFlorain.setText(String.valueOf(currentUser.getWallet().getFlorain()));
    }
    @FXML
    private void OpenCalendar(ActionEvent event) throws SQLException {
        LoadPage.loadPage("Calendrier", event,getClass());

        // Calendrier calendrier = new Calendrier(Session.getInstance().getCurrentUser(), DateTime.now(),ModeAffichage.Mois);
        // AfficheAllRessources(calendrier.getRessourceActif());
        // AfficheAllEvents(calendrier.getEventActif());
        // AfficheInterval(calendrier.getBetweenDate());
    }

    @FXML
    private void OpenMesAnnonces(ActionEvent event){
        LoadPage.loadPage("MesAnnonces", event,getClass());
    }

    @FXML
    private void retourAccueil(ActionEvent event){
        LoadPage.loadPage("Accueil", event,getClass());
    }

    @FXML
    private void afficherMessagerie(ActionEvent event){
        LoadPage.loadPage("Messagerie", event, getClass());
    }

    @FXML
    private void afficherMonCompte(ActionEvent event){
        LoadPage.loadPage("MonCompte", event,getClass());
    }
    @FXML
    private void setDisconnectBtn(ActionEvent event){
        Session.getInstance().setCurrentUser(null);
        LoadPage.loadPage("Connexion", event,getClass());
    }
    public void AfficheAllEvents(ArrayList<EventRessource> events) throws SQLException{
        System.out.println("Affichage des events actifs: ");
        if (events.isEmpty()){
            System.out.println("Aucun event actif");
        }
        for (EventRessource event : events){
            event.AfficheEvent();
        }
    }    
    public void AfficheAllRessources(ArrayList<Ressource> ressources) throws SQLException{
        System.out.println("Affichage des ressources actives: ");
        if (ressources.isEmpty()){
            System.out.println("Aucune ressource active");
        }
        for (Ressource ressource : ressources){
            ressource.AfficheRessource();
        }
    }
    public void AfficheInterval(Interval interval){
        System.out.println("Date de début : " + interval.getStart());
        System.out.println("Date de fin : " + interval.getEnd());
    }
}
