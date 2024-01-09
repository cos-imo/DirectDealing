package eu.telecomnancy.labfx.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import eu.telecomnancy.labfx.Connect;
import eu.telecomnancy.labfx.Session;
import eu.telecomnancy.labfx.User;

import java.sql.*;

public class RechercheController {

    @FXML
    private VBox searchListContainer;

    public void initialize(){

    }
    
    private void addElementToSearchList(String imageUrl, String nomAnnonce, String type, String nomPreteur, String localisation, String prix) {

        ImageView imageView = new ImageView();
        imageView.setFitHeight(105.0);
        imageView.setFitWidth(148.0);
        imageView.setPreserveRatio(true);
        imageView.setImage(new Image(imageUrl));

        Label labelNom = new Label(nomAnnonce);
        labelNom.setFont(new Font(30.0));

        Label labelType = new Label(type);
        labelType.setFont(new Font(10.0));
        labelType.setTextFill(javafx.scene.paint.Color.web("#7c7c7c"));

        Label labelNomPreteur = new Label(nomPreteur);
        labelNomPreteur.setFont(new Font(15.0));

        Label labelLocalisation = new Label(localisation);
        labelLocalisation.setFont(new Font(10.0));
        labelLocalisation.setTextFill(javafx.scene.paint.Color.web("#7c7c7c"));

        Label labelPrix = new Label(prix);

        HBox elementBox = new HBox();
        elementBox.setPrefHeight(104.0);
        elementBox.setPrefWidth(584.0);
        elementBox.getChildren().addAll(imageView, createVBox(labelNom, labelType, labelNomPreteur), createVBox(labelLocalisation, labelPrix));

        Separator separator = new Separator();
        separator.setPrefWidth(200.0);

        searchListContainer.getChildren().addAll(elementBox, separator);
    }

    private VBox createVBox(Label... labels) {
        VBox vbox = new VBox();
        vbox.setPrefHeight(105.0);
        vbox.setPrefWidth(535.0);
        vbox.getChildren().addAll(labels);
        return vbox;
    }

    private int getElements() throws SQLException{
        int max_id = 0;

        Connect connect = new Connect();
        try (Connection connection = connect.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Ressource");
            ResultSet resultSet = preparedStatement.executeQuery();

            System.out.println(resultSet);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return 1;
    }
    
}
