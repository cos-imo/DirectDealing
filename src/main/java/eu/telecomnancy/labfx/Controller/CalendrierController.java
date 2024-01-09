package eu.telecomnancy.labfx.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.Locale;

public class CalendrierController {

    @FXML
    private GridPane calendarGrid;

    @FXML
    private Label monthLabel;

    public void initialize() {
        YearMonth currentMonth = YearMonth.now();
        fillCalendar(currentMonth);
    }

    private void fillCalendar(YearMonth month) {
        LocalDate date = month.atDay(1);
        int dayOfWeek = date.getDayOfWeek().getValue() % 7;
        String[] daysOfWeek = {"Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi", "Dimanche"};
        monthLabel.setText(month.getMonth().getDisplayName(TextStyle.FULL, Locale.FRENCH) + " " + month.getYear());
        calendarGrid.getChildren().clear();

        // Ajouter les en-têtes de jour
        for (int i = 0; i < daysOfWeek.length; i++) {
            Label lbl = new Label(daysOfWeek[i]);
            lbl.setStyle("-fx-font-weight: bold; -fx-padding: 5; -fx-text-fill: #666;");
            calendarGrid.add(lbl, i, 0);
        }

        // Ajouter les jours
        for (int i = 0; i < month.lengthOfMonth(); i++) {
            Label dayLabel = new Label(Integer.toString(i + 1));
            dayLabel.setStyle("-fx-border-color: lightgray; -fx-padding: 5;");
            calendarGrid.add(dayLabel, (dayOfWeek + i) % 7, (dayOfWeek + i) / 7 + 1);
        }
    }
}
