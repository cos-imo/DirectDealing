package eu.telecomnancy.labfx.Controller;


import javafx.fxml.FXML;
import javafx.event.Event;
import javafx.scene.control.DatePicker;
import java.io.File;
import java.nio.file.Files;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.ByteArrayInputStream;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.stage.FileChooser;
import java.time.LocalDate;
import javafx.stage.Stage;
import javafx.scene.control.Slider;
import eu.telecomnancy.labfx.Connect;
import eu.telecomnancy.labfx.Recurrence;
import eu.telecomnancy.labfx.Ressource;
import eu.telecomnancy.labfx.Session;
import javafx.util.converter.IntegerStringConverter;
import java.time.LocalDateTime;
import java.io.File;
import java.util.List;
import java.util.Optional;



import java.sql.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

import org.joda.time.DateTime;

public class AjoutItemControler {

    private byte[] image;

    @FXML
    private TextArea DescriptionField;

    @FXML
    private TextField NomAnnonce;

    @FXML
    private TextField prix_florain;

    @FXML
    private DatePicker DatePickerDebut;
    @FXML
    private ChoiceBox<String> hourDebut;
    @FXML
    private ChoiceBox<String> minuteDebut;

    @FXML
    private DatePicker DatePickerFin;
    @FXML
    private ChoiceBox<String> hourFin;
    @FXML
    private ChoiceBox<String> minuteFin;

    @FXML
    private ChoiceBox<String> choixType;

    @FXML
    private ChoiceBox<String> choixRecurrence;

    @FXML
    private ImageView image_annonce;

    private void setImage(byte[] img){
        this.image = img;
    }

    @FXML
    private void ajouterPhotos(ActionEvent event){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image");
        File selectedFile = fileChooser.showOpenDialog(image_annonce.getScene().getWindow());
        if (selectedFile != null) {
            try {
                byte[] imageBytes = Files.readAllBytes(selectedFile.toPath());
                image_annonce.setImage(new Image(new ByteArrayInputStream(imageBytes)));
                setImage(imageBytes);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private boolean insertDatabase(String Name, String Desc, java.sql.Timestamp DateDebut, java.sql.Timestamp DateFin, float LocalisationLongitude, float LocalisationLatitude, boolean type, int Prix,Recurrence recurrence) throws SQLException{
        Connect connect = new Connect();
        Connection connection = connect.getConnection(); 
        String sql = "INSERT INTO Ressource (Name, Desc, DateDebut, DateFin, LocalisationLongitude, LocalisationLatitude, type, Prix, Image, Owner_id,Recurrence) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, Name);
            preparedStatement.setString(2, Desc);
            preparedStatement.setTimestamp(3, DateDebut);
            preparedStatement.setTimestamp(4, DateFin);
            preparedStatement.setFloat(5, LocalisationLongitude);
            preparedStatement.setFloat(6, LocalisationLatitude);
            preparedStatement.setBoolean(7, type);
            preparedStatement.setInt(8, Prix);
            preparedStatement.setBytes(9, this.image);
            preparedStatement.setInt(10, Session.getInstance().getCurrentUser().getId());
            preparedStatement.setInt(11, Recurrence.getInt(recurrence));

            // Requête d'insertion
            int rowsAffected = preparedStatement.executeUpdate();
            connection.commit();
            preparedStatement.close();
            connection.close();
            // Retourner vrai si une ligne a été insérée, faux sinon
            return rowsAffected > 0;
            }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @FXML
    private int getMaxId() throws SQLException{
        int max_id = 0;

        Connect connect = new Connect();
        try (Connection connection = connect.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT MAX(Ressource_id) FROM Ressource");
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                max_id = resultSet.getInt(1);
            }
            resultSet.close();
            preparedStatement.close();
            connection.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(max_id+1);
        return max_id;
    }

    private void afficherPopupErreur(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();

    }

    private void afficherPopupValidation(ActionEvent event, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Validation");
        alert.setHeaderText(null);
        alert.setContentText(message);
    
        // Ajouter un bouton OK personnalisé pour rediriger vers la page d'accueil
        ButtonType okButton = new ButtonType("OK");
        alert.getButtonTypes().setAll(okButton);
    
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == okButton) {
            // netttoyer les champs
            // DescriptionField.setText("");
            // NomAnnonce.setText("");
            // prix_florain.setText("");
            // DatePickerDebut.setValue(null);
            // DatePickerFin.setValue(null);
            // hourDebut.setValue(null);
            // minuteDebut.setValue(null);
            // hourFin.setValue(null);
            // minuteFin.setValue(null);
            // choixType.setValue(null);
            // choixRecurrence.setValue(null);
            // image_annonce.setImage(new Image("/eu/telecomnancy/labfx/images/kawai.png"));
            LoadPage.loadPage("AjoutItem", event ,getClass());
    }
}


    @FXML
    private void ajouterItem(ActionEvent event) {
        String Description = DescriptionField.getText();
        LocalDate DateDebut = DatePickerDebut.getValue();
        LocalDate DateFin = DatePickerFin.getValue();
        String Nom = NomAnnonce.getText();
        String prixFlorainText = prix_florain.getText();
        String minuteDebutValue = minuteDebut.getValue();
        String minuteFinValue = minuteFin.getValue();
        String hourDebutValue = hourDebut.getValue();
        String hourFinValue = hourFin.getValue();

        if (Description != null && DateDebut != null && DateFin != null && Nom != null && prixFlorainText != "" && minuteDebutValue != null && minuteFinValue != null && hourDebutValue != null && hourFinValue != null && choixRecurrence.getSelectionModel().getSelectedItem() != null && choixType.getSelectionModel().getSelectedItem() != null) {

            int Prix = Integer.valueOf(prixFlorainText);
            boolean type = choixType.getSelectionModel().getSelectedIndex() == 0; // Le premier choix est "objet"
            Recurrence rec = Recurrence.getRecurrence(choixRecurrence.getSelectionModel().getSelectedIndex());

            int hourDebutI = Integer.parseInt(hourDebutValue.substring(0, hourDebutValue.length() - 1));
            int minuteDebutI = Integer.parseInt(minuteDebutValue);
            int hourFinI = Integer.parseInt(hourFinValue.substring(0, hourFinValue.length() - 1));
            int minuteFinI = Integer.parseInt(minuteFinValue);

            LocalDateTime dateTimeDebut = LocalDateTime.of(DateDebut.getYear(), DateDebut.getMonth(), DateDebut.getDayOfMonth(), hourDebutI, minuteDebutI);
            LocalDateTime dateTimeFin = LocalDateTime.of(DateFin.getYear(), DateFin.getMonth(), DateFin.getDayOfMonth(), hourFinI, minuteFinI);

            java.sql.Timestamp sqlTimestampDebut = java.sql.Timestamp.valueOf(dateTimeDebut);
            java.sql.Timestamp sqlTimestampFin = java.sql.Timestamp.valueOf(dateTimeFin);

            try {
                this.insertDatabase(Nom, Description, sqlTimestampDebut, sqlTimestampFin, 0.0f, 0.0f, type, Prix, rec);
                Session.getInstance().getCurrentUser().getRessources().add(Ressource.newRessourceFromId(getMaxId()));
                afficherPopupValidation(event, "Votre annonce a bien été ajoutée.");
                //revenir à a page d'accueil   LoadPage.loadPage("Accueil", event,getClass());
                
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            afficherPopupErreur("Veuillez remplir tous les champs.");
        }
    }

}
