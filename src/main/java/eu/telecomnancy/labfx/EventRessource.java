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
    public EventRessource(EventRessource event, DateTime dateDebut, DateTime dateFin) {
        this.ressource = event.getRessource();
        this.id = event.getId();
        this.idUmprunteur = event.getIdUmprunteur();
        this.idPreteur = event.getIdPreteur();
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
    public void AfficheEvent(){
        System.out.println("Nom de la ressource : " + ressource.getName());
        System.out.println("Date de début de l'évent: " + dateDebut);
        System.out.println("Date de fin de l'évent : " + dateFin);
    }


}
