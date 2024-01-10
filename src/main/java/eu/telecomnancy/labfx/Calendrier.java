package eu.telecomnancy.labfx;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import org.joda.time.Interval;
import org.joda.time.DateTime;


public class Calendrier {
    private User user;
    private DateTime mainDate;
    private ArrayList<EventRessource> eventActif;
    private ArrayList<Ressource> ressourceActif;
    private ModeAffichage modeAffichage;
    private Interval betweenDate;
    
    public Calendrier(User user, DateTime mainDate, ModeAffichage modeAffichage) throws SQLException {
        this.user = user;
        this.mainDate = mainDate;
        this.betweenDate = getBetweenDate();
        this.modeAffichage = modeAffichage;
        this.eventActif = createEventActif(); //Dans les event on regarde ceux qui sont dans l'intervalle
        this.ressourceActif = createRessourceActif(); //Dans les ressources on regarde ceux dont la récurrence est dans l'intervalle
    }
    private Interval getBetweenDate() {
        DateTime dateDebut = null;
        DateTime dateFin = null;
        switch (modeAffichage) {
            case Jour:
                dateDebut = mainDate.withTimeAtStartOfDay();
                dateFin = mainDate.withTimeAtStartOfDay().plusDays(1).minusMinutes(1);
            break;
            case Semaine:
                dateDebut = mainDate.withTimeAtStartOfDay().withDayOfWeek(1);
                dateFin = mainDate.withTimeAtStartOfDay().withDayOfWeek(7).minusMinutes(1);
            break;
            case Mois:
                dateDebut = mainDate.withTimeAtStartOfDay().withDayOfMonth(1);
                dateFin = mainDate.withTimeAtStartOfDay().withDayOfMonth(mainDate.dayOfMonth().getMaximumValue()).minusMinutes(1);            
            break;
        }
        Interval betweenDate = new Interval(dateDebut, dateFin);
        return betweenDate;
    }
    private ArrayList<EventRessource> createEventActif() throws SQLException{
        ArrayList<EventRessource> eventActif = new ArrayList<EventRessource>();
        for (EventRessource event : user.getEventRessource()) {
            Interval betweenEvent = new Interval(event.getDateDebut(), event.getDateFin());
            if (betweenDate.contains(event.getDateDebut()) || betweenDate.contains(event.getDateFin()) || betweenEvent.contains(betweenDate)) {
                eventActif.add(createEventBetweenDate(event));
            }
        }
        return eventActif;
    }
    private ArrayList<Ressource> createRessourceActif() throws SQLException{
        ArrayList<Ressource> ressourceActif = new ArrayList<Ressource>();
        for (Ressource ressource : user.getRessources()) {
            if (betweenDate.contains(ressource.getDateDebut()) || betweenDate.contains(ressource.getDateFin())) {
                ressourceActif.add(ressource);
            } else {
                if (ressource.getDateDebut().isBefore(betweenDate.getStart())){
                    switch (ressource.getReccurence()) {
                        case Quotidien:
                            
                        break;
                        case Hebdomadaire:
                            
                        break;
                        case Mensuel:
                            
                        break;
                        default:
                            break;
                    }
                }
            }
        }
        return ressourceActif;
    }
    private ArrayList<EventRessource> getEventActif() {
        return eventActif;
    }
    public EventRessource createEventBetweenDate(EventRessource event){
        DateTime newDebut = new DateTime();
        DateTime newEnd = new DateTime();
        if (event.getDateDebut().isBefore(betweenDate.getStart())){
            newDebut = betweenDate.getStart();
        } else {
            newDebut = event.getDateDebut();
        }
        if (event.getDateFin().isAfter(betweenDate.getEnd())){
            newEnd = betweenDate.getEnd();
        } else {
            newEnd = event.getDateFin();
        }
        EventRessource newEvent = new EventRessource(event.getRessource(), event.getId(),event.getIdUmprunteur(),newDebut, newEnd);
        return newEvent;
    }
    public int getDaysOccurencePeriod(DateTime mainDate){
        switch (modeAffichage) {
            case Jour:
                return 1;
            case Semaine:
                return 7;
            case Mois:
                return mainDate.dayOfMonth().getMaximumValue();
            default:
                return 0;
        }
    }



}
