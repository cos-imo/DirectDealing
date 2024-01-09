package eu.telecomnancy.labfx.Controller;

import javafx.application.Application;

import javafx.fxml.FXML;
import javafx.event.Event;
import javafx.scene.control.DatePicker;
import javafx.event.ActionEvent;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.stage.FileChooser;
import java.time.LocalDate;
import javafx.stage.Stage;

import java.io.File;
import java.util.List;

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
    private void ajouterPhotos(ActionEvent event){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Images");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif", "*.bmp"));

        Node source = (Node) event.getSource();
        Stage primaryStage = (Stage) source.getScene().getWindow();

        List<File> selectedFiles = fileChooser.showOpenMultipleDialog(primaryStage);

        if (selectedFiles != null) {
            System.out.println("Selected Images:");
            for (File file : selectedFiles) {
                System.out.println(file.getAbsolutePath());
            }
        } else {
            System.out.println("No images selected.");
        }
    }

    @FXML   
    private void ajouterItem(Event event){
        String Description = DescriptionField.getText();
        LocalDate DateDebut = DatePickerDebut.getValue();
        LocalDate DateFin = DatePickerFin.getValue();
        String selectedValue = choixType.getValue();
        String Nom = NomAnnonce.getText();
    }
}
