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
        //Etape 1 : Trouver l'occurence la plus proche de la date de début qui est aussi après la date de début
        //Etape 2 : Ajouter l'occurence à la liste des ressources actives en vérifiant que sa date de fin est avant la date de fin de la période
        //Etape 3 : Quand la date de fin est après la date de fin de la période ou que la date de début est après la date de fin de la période on arrête d'ajouter

        ArrayList<Ressource> ressourceActif = new ArrayList<Ressource>();
        for (Ressource ressource : user.getRessources()) {
            DateTime newDebut = ressource.getDateDebut();
            DateTime newFin = ressource.getDateFin();
            if (ressource.getReccurence() != Recurrence.Non){
                while (newDebut.isBefore(betweenDate.getEnd()) && newDebut.isBefore(newFin)){
                    newDebut = AjoutPeriodRecc(newDebut,ressource.getReccurence());
                    newFin = AjoutPeriodRecc(newFin,ressource.getReccurence());
                }
                while (betweenDate.contains(newFin) || betweenDate.contains(newDebut)){
                    ressourceActif.add(ressourceReducedToAffichage(new Ressource(ressource, newDebut, newFin)));
                    newDebut = AjoutPeriodRecc(newDebut,ressource.getReccurence());
                    newFin = AjoutPeriodRecc(newFin,ressource.getReccurence());
                }
            } else {
                if (betweenDate.contains(ressource.getDateDebut()) || betweenDate.contains(ressource.getDateFin())) {
                    ressourceActif.add(ressourceReducedToAffichage(ressource));
                }
            }
        }
        return ressourceActif;
    }
    private ArrayList<EventRessource> getEventActif() {
        return eventActif;
    }
    public EventRessource createEventBetweenDate(EventRessource event){
        DateTime newDebut = reduceDebutDate(event.getDateDebut());
        DateTime newEnd = reduceFinDate(event.getDateFin());
        EventRessource newEvent = new EventRessource(event,newDebut, newEnd);
        return newEvent;
    }
    
    public DateTime minDate(DateTime date1, DateTime date2){
        if (date1.isBefore(date2)){
            return date1;
        } else {
            return date2;
        }
    }
    public DateTime maxDate(DateTime date1, DateTime date2){
        if (date1.isAfter(date2)){
            return date1;
        } else {
            return date2;
        }
    }
    public DateTime reduceDebutDate(DateTime date1){
        return maxDate(date1, betweenDate.getStart());
    }
    public DateTime reduceFinDate(DateTime date1){
        return minDate(date1, betweenDate.getEnd());
    }
    // public DateTime AjoutPeriodAffichage(DateTime date){
    //     switch (modeAffichage) {
    //         case Jour:
    //             return date.plusDays(1);
    //         case Semaine:
    //             return date.plusWeeks(1);
    //         case Mois:
    //             return date.plusMonths(1);
    //         default:
    //             return null;
    //     }
    // }
    public DateTime AjoutPeriodRecc(DateTime date, Recurrence reccurence){
        switch (reccurence) {
            case Quotidien:
                return date.plusDays(1);
            case Hebdomadaire:
                return date.plusWeeks(1);
            case Mensuel:
                return date.plusMonths(1);
            default:
                return date;
        }
    }
    public Ressource ressourceReducedToAffichage(Ressource r){
        Ressource newRessource = new Ressource(r,reduceDebutDate(r.getDateDebut()),reduceFinDate(r.getDateFin()));
        return newRessource;

    }
    public ArrayList<Ressource> getRessourceActif() {
        return ressourceActif;
    }

}
