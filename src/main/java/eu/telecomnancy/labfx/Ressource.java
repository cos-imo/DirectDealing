package eu.telecomnancy.labfx;

import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.scene.image.Image;

public class Ressource {
    private String name;
    private String description;
    private Date dateDebut;
    private Date dateFin;
    private double longitude;
    private double latitude;
    private int id;
    private Florain prix;
    private Recurrence reccurence;
    private int idOwner;
    private Image pdp;


    public Ressource(String name, String description, Date dateDebut, Date dateFin, double longitude, double latitude, int id, Florain prix, Recurrence reccurence, int id_owner, Image pdp) {
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
    public int getId(){
        return this.id;
    }
    public String getName(){
        return this.name;
    }
    public String getDescription(){
        return this.description;
    }
    public Date getDateDebut(){
        return this.dateDebut;
    }
    public Date getDateFin(){
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
        Date dateDebut = rs.getDate("DateDebut");
        Date dateFin = rs.getDate("DateFin");
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
