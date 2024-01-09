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
    
    private void addElementToSearchList(String name, String desc, String type, String Prix) throws SQLException{
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/eu/telecomnancy/labfx/fxml/ListObject.fxml"));
            Node content = loader.load();

            ListObjectController objectController = loader.getController();

            objectController.setElementData(name, desc, type, Prix);

            searchListContainer.getChildren().addAll(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ResultSet getElements() throws SQLException{

        Connect connect = new Connect();
        try (Connection connection = connect.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Ressource;");
            ResultSet resultSet = preparedStatement.executeQuery();

             while (resultSet.next()) {
                // System.out.print(resultSet.getString(2) + "\t");
                // System.out.print(resultSet.getString(3) + "\t");
                // System.out.print(resultSet.getString(8) + "\t");
                // System.out.print(resultSet.getString(9) + "\t");


                String name = resultSet.getString(2);
                String desc = resultSet.getString(3);
                String type = resultSet.getBoolean(8) ? "Object" : "Service";
                String Prix = resultSet.getString(9);
                addElementToSearchList(name, desc, type, Prix);
            }

            return resultSet;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
