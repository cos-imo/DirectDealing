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
        getEventRessource(); //Initialise ressources // TODO : Changer ca sans dupliquer la réquête SQL
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
            System.out.println("User trouvé");
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
            System.out.println("User créé");
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
        ArrayList<Ressource> ressources = new ArrayList<Ressource>();
        Connect connect = new Connect();
        Connection connection = connect.getConnection(); 
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Event WHERE User_id = "+this.id+";");
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            int id = resultSet.getInt("Event_id");
            int idRessource = resultSet.getInt("Ressource_id");
            int idUmprunteur = resultSet.getInt("User_id");
            Ressource ressource = Ressource.newRessourceFromId(idRessource);
            if (ressource != null){
                EventRessource eventRessource = new EventRessource(ressource, id, idUmprunteur, new DateTime(resultSet.getInt("Date_debut")), new DateTime(resultSet.getInt("Date_fin")));
                eventRessources.add(eventRessource);
                ressources.add(ressource);
            }
        }
        preparedStatement.close();
        connection.commit();
        connection.close();
        this.ressources = ressources;
        return eventRessources;
    }
}
