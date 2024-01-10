package eu.telecomnancy.labfx;

import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.joda.time.DateTime;

import javafx.scene.image.Image;

public class Ressource {
    private String name;
    private String description;
    private DateTime dateDebut;
    private DateTime dateFin;
    private double longitude;
    private double latitude;
    private int id;
    private Florain prix;
    private Recurrence reccurence;
    private int idOwner;
    private Image pdp;


    public Ressource(String name, String description, DateTime dateDebut, DateTime dateFin, double longitude, double latitude, int id, Florain prix, Recurrence reccurence, int id_owner, Image pdp) {
        this.name = name;
        this.description = description;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.id = id;
        this.prix = prix;
        this.reccurence = reccurence;
        this.idOwner = id_owner;
        this.pdp = pdp;
    }
    public Ressource(Ressource ressource, DateTime dateDebut, DateTime dateFin) {
        this.name = ressource.getName();
        this.description = ressource.getDescription();
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.id = ressource.getId();
        this.prix = ressource.getPrix();
        this.reccurence = ressource.getReccurence();
        this.idOwner = ressource.getIdOwner();
        this.pdp = ressource.getPdp();
    }

    public int getId(){
        return this.id;
    }
    public String getName(){
        return this.name;
    }
    public String getDescription(){
        return this.description;
    }
    public DateTime getDateDebut(){
        return this.dateDebut;
    }
    public DateTime getDateFin(){
        return this.dateFin;
    }
    public double getLongitude(){
        return this.longitude;
    }
    public double getLatitude(){
        return this.latitude;
    }
    public Florain getPrix(){
        return this.prix;
    }
    public Recurrence getReccurence(){
        return this.reccurence;
    }
    public int getIdOwner(){
        return this.idOwner;
    }
    public User getOwner() throws SQLException {
        return User.newUserFromId(idOwner);
    }
    public Image getPdp() {
        return pdp;
    }
    public static Ressource newRessourceFromId(int id) throws SQLException{
        String sql = "SELECT * FROM Ressource WHERE id = '"+id+"';";
        return newRessourceFromAttribute(sql);
    }
    private static Ressource newRessourceFromAttribute(String sql) throws SQLException{
        Connect connect = new Connect();
        Connection connection = connect.getConnection();
        Statement statement = connection.createStatement();
        PreparedStatement pstmt = connection.prepareStatement(sql);

        ResultSet rs = pstmt.executeQuery();
        if (rs.next()){ //Se ferme si la requête est vide
            pstmt.close();
            connection.close();
        }
        String name = rs.getString("Name");
        String desc = rs.getString("Desc");
        DateTime dateDebut = new DateTime(rs.getInt("DateDebut"));//TODO: Récupérer dans le bon format en joda Time
        DateTime dateFin = new DateTime(rs.getInt("DateFin")); //TODO : Récupérer le bon format en Joda Time
        double longitude = rs.getDouble("LocalisationLongitude");
        double latitude = rs.getDouble("LocalisationLatitude");
        int id = rs.getInt("Ressource_id");
        Florain prix = new Florain(rs.getInt("Prix"));
        Recurrence reccurence = Recurrence.getRecurrence(rs.getInt("recurrence"));
        int id_owner = rs.getInt("Owner_id");
        byte[] imagebyte = rs.getBytes("Image");
        Image pdp = null;
        if (imagebyte != null && imagebyte.length > 0) {
            pdp = (new Image(new ByteArrayInputStream(imagebyte)));
        }
        return new Ressource(name, desc, dateDebut, dateFin, longitude, latitude, id, prix, reccurence, id_owner,pdp);
    }

}
