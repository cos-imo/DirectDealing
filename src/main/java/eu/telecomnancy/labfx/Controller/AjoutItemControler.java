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
import javafx.util.converter.IntegerStringConverter;

import java.io.File;
import java.util.List;
import java.sql.*;

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
    private DatePicker DatePickerFin;

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

    private boolean insertDatabase(String Name, String Desc, java.sql.Date DateDebut, java.sql.Date DateFin, Float LocalisationLongitude, Float LocalisationLatitude, int type, int Prix) throws SQLException{
        Connect connect = new Connect();
        Connection connection = connect.getConnection(); 
        String sql = "INSERT INTO Ressource (Ressource_Id, Name, Desc, DateDebut, DateFin, LocalisationLongitude, LocalisationLatitude, type, Prix, Image, Owner_id,Recurrence) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 2,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, getMaxId());
            preparedStatement.setString(2, Name);
            preparedStatement.setString(3, Desc);
            preparedStatement.setDate(4, DateDebut);
            preparedStatement.setDate(5, DateFin);
            preparedStatement.setFloat(6, LocalisationLongitude);
            preparedStatement.setFloat(7, LocalisationLatitude);
            preparedStatement.setInt(8, type);
            preparedStatement.setInt(9, Prix);
            preparedStatement.setBytes(10, this.image);
            preparedStatement.setInt(11, 1);

            // Requête d'insertion
            int rowsAffected = preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.commit();
            connection.close();
            // Retourner vrai si une ligne a été insérée, faux sinon
            System.out.println("Ok");
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
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(max_id+1);
        return max_id+1;
    }

    @FXML   
    private void ajouterItem(Event event) {
        String Description = DescriptionField.getText();
        LocalDate DateDebut = DatePickerDebut.getValue();
        LocalDate DateFin = DatePickerFin.getValue();
        String selectedValue = choixType.getValue();
        String Nom = NomAnnonce.getText();
        int Prix = Integer.valueOf(prix_florain.getText());
        java.sql.Date sqlDateDebut = java.sql.Date.valueOf(DateDebut);
        java.sql.Date sqlDateFin = java.sql.Date.valueOf(DateFin);
        try {
            this.insertDatabase(Nom, Description, sqlDateDebut, sqlDateFin, 0.0f, 0.0f, Prix, 20);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
