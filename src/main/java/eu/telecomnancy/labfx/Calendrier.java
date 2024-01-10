package eu.telecomnancy.labfx;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import org.joda.time.Interval;
import org.joda.time.DateTime;


public class Calendrier {
    private User user;
    private Date mainDate;
    private ArrayList<EventRessource> eventActif;
    private ModeAffichage modeAffichage;

    public Calendrier(User user, Date mainDate) {
        this.user = user;
        this.mainDate = mainDate;
        this.eventActif = new ArrayList<EventRessource>();
    }
    private ArrayList<EventRessource> createEventActif() throws SQLException{
        ArrayList<EventRessource> eventActif = new ArrayList<EventRessource>();
        Interval betweenDate = getBetweenDate();
        for (EventRessource event : user.getEventRessource()) {
            if (betweenDate.contains(event.getDateDebut().getTime()) || betweenDate.contains(event.getDateFin().getTime())) {
                eventActif.add(event);
            }
        }
        return eventActif;
    }
    private ArrayList<EventRessource> getEventActif() {
        return eventActif;
    }

    private Interval getBetweenDate() {
        switch (modeAffichage) {
            case Jour:
            
            break;
            case Semaine:
            
            break;
            case Mois:
            
            break;
            default:
            break;
        }
        Interval betweenDate = new Interval(new DateTime(mainDate), new DateTime(mainDate));
        return betweenDate;
    }

}
