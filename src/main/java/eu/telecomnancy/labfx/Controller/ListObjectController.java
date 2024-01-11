package eu.telecomnancy.labfx.Controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import eu.telecomnancy.labfx.Connect;
import eu.telecomnancy.labfx.Session;

import java.text.SimpleDateFormat;
import java.sql.*;

public class ListObjectController{

    @FXML
    private Label label_nom;

    @FXML
    private Label label_type;

    @FXML
    private Label label_nomPreteur;

    @FXML
    private Label prix_florains;

    @FXML
    private ImageView image_annonce;

    @FXML
    private Label label_date;

    @FXML
    private Button BtnContacter;

    private int ressource_id;
    private String annonceName;
    private Boolean type;
    private String desc;
    private String cout;
    private int owner_id;
    private Date dateDebutAnnonce;
    private Date dateFinAnnonce;
    private Image image;

    private boolean notEnoughFlorains = true;

    public void setElementData(String annonceName,String desc, Boolean type, String cout, Image image, java.sql.Date dateDebut, java.sql.Date dateFin, int ressource_id, int owner_id){
        
        this.ressource_id = ressource_id;
        this.annonceName = annonceName;
        this.type = type;
        this.desc = desc;
        this.cout = cout;
        this.owner_id = owner_id;
        this.image = image;

        notEnoughFlorains = Session.getInstance().getCurrentUser().getWallet().getFlorain() < Integer.parseInt(cout);
        BtnContacter.setDisable(notEnoughFlorains);

        label_nom.setText(annonceName);
        label_type.setText(type ? "Objet" : "Service");
        label_nomPreteur.setText(desc);
        prix_florains.setText(cout);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateDebutAnnonce = dateDebut;
        dateFinAnnonce = dateFin;
        label_date.setText("Du " + dateFormat.format(dateDebutAnnonce.getTime()) + " au " + dateFormat.format(dateFinAnnonce.getTime()));
        if (image_annonce!=null){
            image_annonce.setImage(image);
        }
    }
    
    @FXML
    private void setBtnContacter(ActionEvent event) throws Exception {
        String message_base = "Bonjour, je suis intéressé par votre offre :)";
        String query = "INSERT INTO Message (Ressource_liee_id, Sender_id, Receiver_id, Date, Contenu) VALUES (?, ?, ?, ?, ?);";
        Connect connect = new Connect();
        try (Connection connection = connect.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, ressource_id);
            preparedStatement.setInt(2, Session.getInstance().getCurrentUser().getId());
            preparedStatement.setInt(3, owner_id);
            preparedStatement.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
            preparedStatement.setString(5, message_base);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.commit();
            connection.close();
            System.out.println("Message envoyé");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        BorderPane root = new BorderPane();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/eu/telecomnancy/labfx/fxml/Messagerie.fxml"));

        Node source = (Node) event.getSource();

        Scene scene = new Scene(root, 1080, 720);

        try {
            root.setCenter(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }

        MessagerieController controller = loader.getController();
        controller.setChat(message_base, this.annonceName, owner_id, ressource_id);

        Stage primaryStage = (Stage) source.getScene().getWindow();

        primaryStage.setTitle("TelecomNancy DirectDealing");

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}