package eu.telecomnancy.labfx.Controller;


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
    private void OpenCalendar(ActionEvent event) {
        LoadPage.loadPage("Calendrier", event,getClass());
    }

    @FXML
    private void retourAccueil(ActionEvent event){
        LoadPage.loadPage("Accueil", event,getClass());
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
}
