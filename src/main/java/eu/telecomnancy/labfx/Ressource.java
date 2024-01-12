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
    private float longitude;
    private float latitude;
    private int id;
    private Florain prix;
    private Recurrence reccurence;
    private int idOwner;
    private Image pdp;
    private boolean type;


    public Ressource(String name, String description, DateTime dateDebut, DateTime dateFin, float longitude, float latitude, int id, Florain prix, Recurrence reccurence, int id_owner, Image pdp, boolean type) {
        this.name = name;
        this.description = description;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.id = id;
        this.prix = prix;
        this.reccurence = reccurence;
        this.idOwner = id_owner;
        this.pdp = pdp;
        this.type = type;
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
        this.type = ressource.getType();
    }
    public boolean getType(){
        return this.type;
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
        String sql = "SELECT * FROM Ressource WHERE Ressource_id = '"+id+"';";
        return newRessourceFromAttribute(sql);
    }
    private static Ressource newRessourceFromAttribute(String sql) throws SQLException{
        Connect connect = new Connect();
        Connection connection = connect.getConnection();
        Statement statement = connection.createStatement();
        PreparedStatement pstmt = connection.prepareStatement(sql);

        ResultSet rs = pstmt.executeQuery();
        if (rs.next()){ //Se ferme si la requête est vide
            String name = rs.getString("Name");
            String desc = rs.getString("Desc");
            DateTime dateDebut = new DateTime(rs.getLong("DateDebut"));//TODO: Récupérer dans le bon format en joda Time
            DateTime dateFin = new DateTime(rs.getLong("DateFin")); //TODO : Récupérer le bon format en Joda Time
            float longitude = rs.getFloat("LocalisationLongitude");
            float latitude = rs.getFloat("LocalisationLatitude");
            int idR = rs.getInt("Ressource_id");
            Florain prix = new Florain(rs.getInt("Prix"));
            Recurrence reccurence = Recurrence.getRecurrence(rs.getInt("recurrence"));
            int id_owner = rs.getInt("Owner_id");
            byte[] imagebyte = rs.getBytes("Image");

            Image pdp = null;
            if (imagebyte != null && imagebyte.length > 0) {
                pdp = (new Image(new ByteArrayInputStream(imagebyte)));
            }
            boolean type = rs.getBoolean("type");
            pstmt.close();
            connection.close();
            return new Ressource(name, desc, dateDebut, dateFin, longitude, latitude, idR, prix, reccurence, id_owner,pdp,type);
        }
        return null;
    }
    public void AfficheRessource() {
        System.out.println("Name : " + this.name);
        System.out.println("Description : " + this.description);
        System.out.println("Date de début : " + this.dateDebut);
        System.out.println("Date de fin : " + this.dateFin);
        System.out.println("Longitude : " + this.longitude);
        System.out.println("Latitude : " + this.latitude);
        System.out.println("Id : " + this.id);
        System.out.println("Prix : " + this.prix);
        System.out.println("Reccurence : " + this.reccurence);
        System.out.println("Id Owner : " + this.idOwner);
    }

}
