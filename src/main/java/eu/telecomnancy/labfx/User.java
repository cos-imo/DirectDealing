package eu.telecomnancy.labfx;

import java.io.ByteArrayInputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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

    public User(String email, String password, String nom, String prenom, Image pdp, int id, int wallet, int note) {
        this.email = email;
        this.password = password;
        this.nom = nom;
        this.prenom = prenom;
        this.pdp = pdp;
        this.id = id;
        this.wallet = new Florain(wallet);
        this.note = note;
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
    public static User newUserFromMail(String mail) throws SQLException{
        Connect connect = new Connect();
        Connection connection = connect.getConnection(); 
        String sql = "SELECT * FROM User WHERE Mail = ?";
        Statement statement = connection.createStatement();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, mail);
        System.out.println("Passe par ici");
        ResultSet resultSet = preparedStatement.executeQuery();
        if (!resultSet.isClosed()){
            String email = resultSet.getString("Mail");
            String password = resultSet.getString("Password");
            String nom = resultSet.getString("Last_Name");
            String prenom = resultSet.getString("First_Name");
            int id = resultSet.getInt("User_ID");
            int wallet = resultSet.getInt("Wallet");
            int note = resultSet.getInt("Note");
            byte[] imagebyte = resultSet.getBytes("Photo_profil");
            if (imagebyte != null && imagebyte.length > 0) {
                Image pdp = (new Image(new ByteArrayInputStream(imagebyte)));
                User user = new User(email, password, nom, prenom,pdp, id, wallet, note);
                preparedStatement.close();
                connection.commit();
                connection.close();
                System.out.println("User créé");
                return user;
            }
        }
        connection.close();
        return null;
    }
}
