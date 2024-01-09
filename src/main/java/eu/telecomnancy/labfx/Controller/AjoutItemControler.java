package eu.telecomnancy.labfx.Controller;
import javafx.fxml.FXML;
import javafx.event.Event;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ChoiceBox;
import java.time.LocalDate;

public class AjoutItemControler {

    @FXML
    private TextArea DescriptionField;

    @FXML
    private TextField NomAnnonce;

    @FXML
    private DatePicker DatePickerDebut;

    @FXML
    private DatePicker DatePickerFin;

    @FXML
    private ChoiceBox<String> choixType;

    @FXML   
    private void ajouterItem(Event event){
        String Description = DescriptionField.getText();
        LocalDate DateDebut = DatePickerDebut.getValue();
        LocalDate DateFin = DatePickerFin.getValue();
        String selectedValue = choixType.getValue();
        String Nom = NomAnnonce.getText();
    }

    @FXML
    private void ajouterPhotos(){
        System.out.println("Ajout de photos");
    }
}
