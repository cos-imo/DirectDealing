package eu.telecomnancy.labfx.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import java.util.List;
import java.io.ByteArrayInputStream;
import java.lang.Object;
import eu.telecomnancy.labfx.Connect;
import java.io.IOException;
import java.sql.*;

public class RechercheController {

    @FXML
    private VBox searchListContainer;

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
                if (resultSet.getBytes("Illustration") != null) {
                    image = new Image(new ByteArrayInputStream(resultSet.getBytes("Illustration")));
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
