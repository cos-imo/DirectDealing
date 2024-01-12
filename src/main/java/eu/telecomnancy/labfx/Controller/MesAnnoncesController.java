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
import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.lang.Object;
import javafx.scene.control.DatePicker;
import eu.telecomnancy.labfx.Connect;
import javafx.scene.control.TextField;
import javafx.scene.control.ChoiceBox;
import java.io.IOException;
import java.sql.*;

public class MesAnnoncesController {

    @FXML
    private VBox searchListContainer;

    @FXML
    private DatePicker pickerDebut;

    @FXML
    private DatePicker pickerFin;
    
    @FXML
    private ChoiceBox<String> type;
    
    @FXML
    private TextField prix_min;
    
    @FXML
    private TextField prix_max;

    public void initialize() throws SQLException {
        getElements();
    }
    
    private void addElementToSearchList(String name, String desc, Boolean type, String Prix, Image image ,java.sql.Date dateDebut, java.sql.Date dateFin, int ressource_id, int owner_id) throws SQLException{
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/eu/telecomnancy/labfx/fxml/ListObject.fxml"));
            Node content = loader.load();

            ListObjectController objectController = loader.getController();

            objectController.setElementData(name, desc, type, Prix, image, dateDebut, dateFin, ressource_id, owner_id);

            searchListContainer.getChildren().addAll(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isDisponible(int ressource_id) throws SQLException {
        Connect connect = new Connect();
        try (Connection connection = connect.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Event WHERE Ressource_id = ?;");
            preparedStatement.setInt(1, ressource_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return false;
            }
            else {
                return true;
            }
        }
    }
 
    private int getElements() throws SQLException{

        Connect connect = new Connect();
        try (Connection connection = connect.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Ressource;");
            ResultSet resultSet = preparedStatement.executeQuery();
            Image image = null;

             while (resultSet.next()) {

                int owner_id = resultSet.getInt("Owner_id");
                int ressource_id = resultSet.getInt("Ressource_id");
                String name = resultSet.getString("Name");
                String desc = resultSet.getString("Desc");
                Boolean type = resultSet.getBoolean("type");
                String Prix = resultSet.getString("Prix");
                java.sql.Date dateDebut = new java.sql.Date(resultSet.getLong("DateDebut"));
                java.sql.Date dateFin = new java.sql.Date(resultSet.getLong("DateFin"));
                if (resultSet.getBytes("Image") != null) {
                    image = new Image(new ByteArrayInputStream(resultSet.getBytes("Image")));
                } else {
                    System.out.println("Image nulle?");
                }

                if (isDisponible(ressource_id)) {
                    addElementToSearchList(name, desc, type, Prix, image, dateDebut, dateFin, ressource_id, owner_id);
                }
            }
            resultSet.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

}
