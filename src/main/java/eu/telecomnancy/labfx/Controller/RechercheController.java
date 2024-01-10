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
import javafx.scene.text.Font;
import java.util.List;
import java.io.ByteArrayInputStream;
import java.lang.Object;
import javafx.scene.control.DatePicker;
import eu.telecomnancy.labfx.Connect;
import javafx.scene.control.TextField;
import javafx.scene.control.ChoiceBox;
import java.io.IOException;
import java.sql.*;

public class RechercheController {

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
    
    private void addElementToSearchList(String name, String desc, String type, String Prix, Image image) throws SQLException{
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/eu/telecomnancy/labfx/fxml/ListObject.fxml"));
            Node content = loader.load();

            ListObjectController objectController = loader.getController();

            objectController.setElementData(name, desc, type, Prix, image);

            searchListContainer.getChildren().addAll(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int getElements() throws SQLException{

        Connect connect = new Connect();
        try (Connection connection = connect.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Ressource;");
            ResultSet resultSet = preparedStatement.executeQuery();
            Image image = null;

             while (resultSet.next()) {

                String name = resultSet.getString("Name");
                String desc = resultSet.getString("Desc");
                String type = resultSet.getBoolean("type") ? "Objet" : "Service";
                String Prix = resultSet.getString("Prix");
                if (resultSet.getBytes("Image") != null) {
                    image = new Image(new ByteArrayInputStream(resultSet.getBytes("Image")));
                } else {
                    System.out.println("Image nulle?");
                }

                addElementToSearchList(name, desc, type, Prix, image);
            }
            resultSet.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @FXML
    private int searchFilters(ActionEvent event) throws SQLException{

        searchListContainer.getChildren().clear();

        Connect connect = new Connect();
        try (Connection connection = connect.getConnection()){

            String query = "SELECT * FROM Ressource";

            String val_prix_min = prix_min.getText();
            String selectedType = type.getValue();
            
            if (!val_prix_min.isEmpty()) {
                query += " WHERE Prix>= " + Integer.valueOf(val_prix_min);
            }
            // if (!PickerDebut.isEmpty()) {
            //     query += " WHERE DateDebut<= " + pickerDebut.getValue() + " OR DateFin>=" + pickerDebut.getValue();
            // }
            // if (!PickerFin.isEmpty()) {
            //     query += " WHERE DateDebut<= " + pickerDebut.getValue() + " OR DateFin>=" + pickerDebut.getValue();
            // }
            if (!selectedType.isEmpty()) {
                query += " WHERE type = " + "Objet".equals(selectedType);
            }
            
            query += ";";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            Image image = null;

             while (resultSet.next()) {

                String name = resultSet.getString("Name");
                String desc = resultSet.getString("Desc");
                String type = resultSet.getBoolean("type") ? "Objet" : "Service";
                String Prix = resultSet.getString("Prix");
                if (resultSet.getBytes("Image") != null) {
                    image = new Image(new ByteArrayInputStream(resultSet.getBytes("Image")));
                } else {
                    System.out.println("Image nulle?");
                }

                addElementToSearchList(name, desc, type, Prix, image);
            }
            resultSet.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

}
