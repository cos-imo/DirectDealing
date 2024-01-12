package eu.telecomnancy.labfx;

import java.io.ByteArrayInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.joda.time.DateTime;

import javafx.scene.image.Image;


public class User {
    private String email;
    private String password;
    private String nom;
    private String prenom;
    private Image pdp;
    private int id;
    private Florain wallet;
    private int note;
    private ArrayList<Ressource> ressources;

    public User(String email, String password, String nom, String prenom, Image pdp, int id, int wallet, int note) throws SQLException {
        this.email = email;
        this.password = password;
        this.nom = nom;
        this.prenom = prenom;
        this.pdp = pdp;
        this.id = id;
        this.wallet = new Florain(wallet);
        this.note = note;
        this.ressources = createRessources();
        // getEventRessource(); //Initialise ressources // TODO : Changer ca sans dupliquer la réquête SQL
    }

    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }
    public String getNom() {
        return nom;
    }
    public String getPrenom() {
        return prenom;
    }
    public Image getPdp() {
        return pdp;
    }
    public int getId() {
        return id;
    }
    public Florain getWallet() {
        return wallet;
    }
    public int getNote() {
        return note;
    }
    public ArrayList<Ressource> getRessources() {
        return ressources;
    }
    public ArrayList<Ressource> createRessources() throws SQLException{
        ArrayList<Ressource> ressourcess = new ArrayList<Ressource>();
        Connect connect = new Connect();
        Connection connection = connect.getConnection();
        String sql = "SELECT * FROM Ressource WHERE Owner_id = "+this.id+";";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            int id = resultSet.getInt("Ressource_id");
            int idOwner = resultSet.getInt("Owner_id");
            String name = resultSet.getString("Name");
            String desc = resultSet.getString("Desc");
            DateTime dateDebut = new DateTime(resultSet.getLong("DateDebut"));
            DateTime dateFin = new DateTime(resultSet.getLong("DateFin"));
            float longitude = resultSet.getFloat("LocalisationLongitude");
            float latitude = resultSet.getFloat("LocalisationLatitude");
            boolean type = resultSet.getBoolean("type");
            int prix = resultSet.getInt("Prix");
            byte[] imagebyte = resultSet.getBytes("Image");
            Image image = null;
            if (imagebyte != null && imagebyte.length > 0) {
                image = (new Image(new ByteArrayInputStream(imagebyte)));
            }

            Recurrence recurrence = Recurrence.getRecurrence(resultSet.getInt("Recurrence"));
            Ressource ressource = new Ressource(name, desc, dateDebut, dateFin, longitude, latitude, id, new Florain(prix), recurrence, idOwner, image, type);
            ressourcess.add(ressource);
        }
        preparedStatement.close();
        connection.commit();
        connection.close();
        return ressourcess;
    }
    public static User newUserFromMail(String mail) throws SQLException{
        String sql = "SELECT * FROM User WHERE Mail = '"+mail+"';";
        return newUserFromAttribute(sql);
        
    }
    public static User newUserFromId(int id) throws SQLException{
        String sql = "SELECT * FROM User WHERE User_id = "+id+";";
        return newUserFromAttribute(sql);
    }
    public static User newUserFromAttribute(String sql) throws SQLException {
        Connect connect = new Connect();
        Connection connection = connect.getConnection(); 
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (!resultSet.isClosed()){
            String email = resultSet.getString("Mail");
            String password = resultSet.getString("Password");
            String nom = resultSet.getString("Last_Name");
            String prenom = resultSet.getString("First_Name");
            int id = resultSet.getInt("User_id");
            int wallet = resultSet.getInt("Wallet");
            int note = resultSet.getInt("Note");
            byte[] imagebyte = resultSet.getBytes("Photo_profil");
            Image pdp = null;
            if (imagebyte != null && imagebyte.length > 0) {
                pdp = (new Image(new ByteArrayInputStream(imagebyte)));
            }
            User user = new User(email, password, nom, prenom,pdp, id, wallet, note);
            preparedStatement.close();
            connection.commit();
            connection.close();
            return user;
        }
        connection.close();
        return null;
    }
    public static String getHashedPassword(String pass){
         String password = pass;

        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-256");
            md.update(password.getBytes());
    
            byte byteData[] = md.digest();
    
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < byteData.length;i++) {
            sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }
        System.out.println("Hex format : " + sb.toString());
        return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public ArrayList<EventRessource> getEventRessource() throws SQLException {
        ArrayList<EventRessource> eventRessources = new ArrayList<EventRessource>();
        Connect connect = new Connect();
        Connection connection = connect.getConnection(); 
        String sql = """
        SELECT e.Event_id as eventId,e.isObjet as isObject, e.Ressource_id as ressourceId, e.isObjet as isObject, e.Name as eventName, e.preteur_id as preteur_id, e.acheteur_id as acheteur_id, r.Recurrence as Recurrence, e.DateDebut as eventDateDebut, e.DateFin as eventDateFin, r.Owner_id as Owner_id, r.Name as ressourceName, r.Desc as Desc, r.DateDebut as RDateDebut, r.DateFin as RDateFin, r.LocalisationLatitude as LocalisationLatitude, r.LocalisationLongitude as LocalisationLongitude, r.type as Rtype, r.Prix as RPrix, r.Image as RImage
        FROM Event AS e 
        JOIN Ressource AS r 
        ON e.Ressource_id = r.Ressource_id
        WHERE e.acheteur_id = ? OR e.preteur_id = ?;        
                """;
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, this.id);
        preparedStatement.setInt(2, this.id);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            int id = resultSet.getInt("eventId");
            int idRessource = resultSet.getInt("ressourceId");
            int idPreteur = resultSet.getInt("preteur_id");
            int idAcheteur = resultSet.getInt("acheteur_id");
            boolean isObject = resultSet.getBoolean("isObject");
            Ressource ressource = new Ressource(resultSet.getString("ressourceName"), resultSet.getString("Desc"), new DateTime(resultSet.getInt("RDateDebut")), new DateTime(resultSet.getInt("RDateFin")), idRessource, idPreteur, idAcheteur, new Florain(resultSet.getInt("RPrix")), Recurrence.getRecurrence(resultSet.getInt("Recurrence")), id, pdp,isObject);
            EventRessource eventRessource = new EventRessource(ressource, id,idPreteur, idAcheteur, new DateTime(resultSet.getInt("eventDateDebut")), new DateTime(resultSet.getInt("eventDateFin")));
            eventRessources.add(eventRessource);
            System.out.println("Event (getEventRessource) : " + eventRessource.getName() + " Date de début : " + eventRessource.getDateDebut() + " Date de fin : " + eventRessource.getDateFin());
        }
        preparedStatement.close();
        connection.commit();
        connection.close();
        return eventRessources;
    }
}
