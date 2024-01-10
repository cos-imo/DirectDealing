package eu.telecomnancy.labfx;

import org.joda.time.DateTime;

public class EventRessource {
    private Ressource ressource;
    private int id;
    private int idUmprunteur;
    private int idPreteur;
    private DateTime dateDebut;
    private DateTime dateFin;

    public EventRessource(Ressource ressource, int id,int idPreteur, int idUmprunteur, DateTime dateDebut, DateTime dateFin) {
        this.ressource = ressource;
        this.id = id;
        this.idUmprunteur = idUmprunteur;
        this.idPreteur = idPreteur;
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
    public DateTime getDateDebut() {
        return dateDebut;
    }
    public DateTime getDateFin() {
        return dateFin;
    }
    public int getIdPreteur() {
        return idPreteur;
    }



}
