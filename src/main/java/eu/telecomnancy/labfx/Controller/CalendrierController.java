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
        int dayOfWeek = date.getDayOfWeek().getValue() % 7; // Lundi est 1, Dimanche est 0

        monthLabel.setText(month.getMonth().getDisplayName(TextStyle.FULL, Locale.FRENCH) + " " + month.getYear());

        // Effacer le contenu précédent
        calendarGrid.getChildren().clear();

        // Remplir les jours
        for (int i = 0; i < month.lengthOfMonth(); i++) {
            Label dayLabel = new Label(Integer.toString(i + 1));
            calendarGrid.add(dayLabel, (dayOfWeek + i) % 7, (dayOfWeek + i) / 7);
        }
    }
}
