package eu.telecomnancy.labfx;

import java.sql.Date;

public class EventRessource {
    private Ressource ressource;
    private int id;
    private int idUmprunteur;
    private Date dateDebut;
    private Date dateFin;

    public EventRessource(Ressource ressource, int id, int idUmprunteur, Date dateDebut, Date dateFin) {
        this.ressource = ressource;
        this.id = id;
        this.idUmprunteur = idUmprunteur;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }
    public int getIdOwner() {
        return ressource.getIdOwner();
    }
    public int getId() {
        return id;
    }
    public int getIdUmprunteur() {
        return idUmprunteur;
    }
    public Ressource getRessource() {
        return ressource;
    }
    public Date getDateDebut() {
        return dateDebut;
    }
    public Date getDateFin() {
        return dateFin;
    }



}
