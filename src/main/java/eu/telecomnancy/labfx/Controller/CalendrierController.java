package eu.telecomnancy.labfx.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import java.time.YearMonth;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.time.DayOfWeek;
import java.util.Locale;

public class CalendrierController {

    @FXML
    private GridPane calendarGrid;
    @FXML
    private Label monthYearLabel;
    private YearMonth currentYearMonth;

    @FXML
    private void previousMonth() {
        System.out.println("Previous month");
        currentYearMonth = currentYearMonth.minusMonths(1);
        fillCalendar(currentYearMonth);
        updateMonthYearDisplay(currentYearMonth);
    }

    @FXML
    private void currentMonth() {
        System.out.println("Current month");
        currentYearMonth = YearMonth.now();
        fillCalendar(currentYearMonth);
        updateMonthYearDisplay(currentYearMonth);
    }

    @FXML
    private void nextMonth() {
        System.out.println("Next month");
        currentYearMonth = currentYearMonth.plusMonths(1);
        fillCalendar(currentYearMonth);
        updateMonthYearDisplay(currentYearMonth);
    }

    public void initialize() {
        Locale.setDefault(Locale.FRANCE); // Set the locale for France
        currentYearMonth = YearMonth.now(); 
        fillCalendar(currentYearMonth);
        updateMonthYearDisplay(currentYearMonth);
    }
    

    private void fillCalendar(YearMonth yearMonth) {
        // Clear previous calendar entries
        calendarGrid.getChildren().clear();
        
        // Determine the starting day for the calendar
        LocalDate calendarStart = yearMonth.atDay(1).minusDays(yearMonth.atDay(1).getDayOfWeek().getValue() - DayOfWeek.MONDAY.getValue());
        
        // Fill the calendar with days
        LocalDate date = calendarStart;
        int weekRow = 1;

        DayOfWeek[] days = DayOfWeek.values();
        for (int i = 0; i < days.length; i++) {
            String dayDisplayName = days[i].getDisplayName(TextStyle.FULL, Locale.FRENCH);
            Label lblDay = new Label(dayDisplayName.substring(0, 1).toUpperCase() + dayDisplayName.substring(1)); // Capitalize the first letter
            lblDay.getStyleClass().add("calendar-day-header");
            calendarGrid.add(lblDay, i, 0);
}
        while (date.getMonthValue() <= yearMonth.getMonthValue()) {
            for (int j = 0; j < 7; j++) { // Weekday columns
                Label dayLabel = new Label(String.valueOf(date.getDayOfMonth()));
                dayLabel.getStyleClass().add("calendar-day-label");
                if(date.getMonthValue() != yearMonth.getMonthValue()) {
                    dayLabel.getStyleClass().add("calendar-day-label-inactive");
                }
                calendarGrid.add(dayLabel, j, weekRow);
                date = date.plusDays(1);
            }
            // If the row is completely in the next month, remove it
            if (date.getMonthValue() != yearMonth.getMonthValue() && date.getDayOfWeek() == DayOfWeek.MONDAY) {
                break;
            }
            weekRow++;
        }
    }

    private void updateMonthYearDisplay(YearMonth yearMonth) {
        String monthYear = yearMonth.format(DateTimeFormatter.ofPattern("MMMM yyyy", Locale.FRANCE));
        monthYearLabel.setText(monthYear.substring(0, 1).toUpperCase(Locale.FRANCE) + monthYear.substring(1));
    }
}
