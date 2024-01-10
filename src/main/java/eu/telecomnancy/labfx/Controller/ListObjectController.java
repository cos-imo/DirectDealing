package eu.telecomnancy.labfx.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import java.io.ByteArrayInputStream;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import eu.telecomnancy.labfx.Connect;
import java.io.IOException;
import java.sql.*;

public class ListObjectController{

    @FXML
    Label label_nom;

    @FXML
    Label label_type;

    @FXML
    Label label_nomPreteur;

    @FXML
    Label prix_florains;

    @FXML
    ImageView image_annonce;

    public void setElementData(String annonceName, String type, String preteur, String cout, Image image){
        label_nom.setText(annonceName);
        label_type.setText(type);
        label_nomPreteur.setText(preteur);
        prix_florains.setText(cout);
        if (image_annonce!=null){
            image_annonce.setImage(image);
        }
    }
}