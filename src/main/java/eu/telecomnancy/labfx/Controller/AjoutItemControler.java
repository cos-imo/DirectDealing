package eu.telecomnancy.labfx.Controller;


import javafx.fxml.FXML;
import javafx.event.Event;
import javafx.scene.control.DatePicker;
import java.io.File;
import java.nio.file.Files;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.ByteArrayInputStream;
import javafx.event.ActionEvent;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.Node;
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
import java.sql.*;

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

    @FXML   
    private void ajouterItem(Event event) {
        String Description = DescriptionField.getText();
        LocalDate DateDebut = DatePickerDebut.getValue();
        LocalDate DateFin = DatePickerFin.getValue();
        String Nom = NomAnnonce.getText();
        int Prix = Integer.valueOf(prix_florain.getText());
        boolean type = choixType.getSelectionModel().getSelectedIndex() == 0; //Le premier choix est "objet"
        Recurrence rec = Recurrence.getRecurrence(choixRecurrence.getSelectionModel().getSelectedIndex());
        // Convertir les heures et les minutes en entiers
        String minuteDebutS = this.minuteDebut.getValue();
        String minuteFinS = this.minuteFin.getValue();
        int hourDebutI = Integer.parseInt(hourDebut.getValue().substring(0, hourDebut.getValue().length()-1));
        int minuteDebutI = Integer.parseInt(minuteDebutS);
        int hourFinI = Integer.parseInt(hourFin.getValue().substring(0, hourFin.getValue().length()-1));
        int minuteFinI = Integer.parseInt(minuteFinS);

        // System.out.println("Heure début (entier): " + hourDebutI);
        // System.out.println("Minute début (entier): " + minuteDebutI);
        // System.out.println("Heure fin (entier): " + hourFinI);
        // System.out.println("Minute fin (entier): " + minuteFinI);

        // Créer les LocalDateTime
        LocalDateTime dateTimeDebut = LocalDateTime.of(DatePickerDebut.getValue().getYear(), DatePickerDebut.getValue().getMonth(), DatePickerDebut.getValue().getDayOfMonth(), hourDebutI, minuteDebutI);
        LocalDateTime dateTimeFin = LocalDateTime.of(DatePickerFin.getValue().getYear(), DatePickerFin.getValue().getMonth(), DatePickerFin.getValue().getDayOfMonth(), hourFinI, minuteFinI);
        // System.out.println("Date début: " + dateTimeDebut);
        // System.out.println("Date fin: " + dateTimeFin);

        // Convertir les LocalDateTime en java.sql.Date
        // System.out.println("Date début (toLocateDate): " + dateTimeDebut.toLocalDate());
        // System.out.println("Date fin (toLocateDate): " + dateTimeFin.toLocalDate());
        // java.sql.Date sqlDateDebut = java.sql.Date.valueOf(dateTimeDebut.toLocalDate());
        // java.sql.Date sqlDateFin = java.sql.Date.valueOf(dateTimeFin.toLocalDate());
        // System.out.println("Date début (sqlDate): " + sqlDateDebut);
        // System.out.println("Date fin (sqlDate): " + sqlDateFin);

        // Convertir les LocalDateTime en java.sql.Date mais avec les secondes
        java.sql.Timestamp sqlTimestampDebut = java.sql.Timestamp.valueOf(dateTimeDebut);
        java.sql.Timestamp sqlTimestampFin = java.sql.Timestamp.valueOf(dateTimeFin);
        // System.out.println("Date début (sqlTimestamp): " + sqlTimestampDebut);
        // System.out.println("Date fin (sqlTimestamp): " + sqlTimestampFin);


        try {
            this.insertDatabase(Nom, Description, sqlTimestampDebut, sqlTimestampFin, 0.0f, 0.0f, type, Prix,rec);
            Session.getInstance().getCurrentUser().getRessources().add(Ressource.newRessourceFromId(getMaxId()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
