package eu.telecomnancy.labfx.Controller;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.RowConstraints;
import javafx.scene.control.Label;
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

    public void initialize() {
        Locale.setDefault(new Locale("fr", "FR"));
        YearMonth currentYearMonth = YearMonth.now();
        fillCalendar(currentYearMonth);
    }
    private void updateMonthYearDisplay(YearMonth yearMonth) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy").withLocale(Locale.FRENCH);
        String monthYear = yearMonth.format(formatter);
        monthYearLabel.setText(monthYear.substring(0, 1).toUpperCase() + monthYear.substring(1)); // Capitalize the first letter
    }

    private void fillCalendar(YearMonth yearMonth) {
        // Clear existing calendar
        calendarGrid.getChildren().clear();
        calendarGrid.getColumnConstraints().clear();
        calendarGrid.getRowConstraints().clear();

        // Configure grid size
        int totalColumns = DayOfWeek.values().length; // 7 days in a week
        for (int i = 0; i < totalColumns; i++) {
            ColumnConstraints column = new ColumnConstraints(100); // Column width
            calendarGrid.getColumnConstraints().add(column);
        }

        

        // Add day of week headers
        DayOfWeek[] days = DayOfWeek.values();
        for (int i = 0; i < days.length; i++) {
            String dayDisplayName = days[i].getDisplayName(TextStyle.FULL, Locale.FRENCH);
            Label lblDay = new Label(dayDisplayName.substring(0, 1).toUpperCase() + dayDisplayName.substring(1)); // Capitalize the first letter
            lblDay.getStyleClass().add("calendar-day-header");
            calendarGrid.add(lblDay, i, 0);
        }

        // Fill days
        LocalDate calendarDate = yearMonth.atDay(1);
        while (calendarDate.getDayOfWeek() != DayOfWeek.MONDAY) {
            calendarDate = calendarDate.minusDays(1);
        }

        int rowCounter = 1;
        while (rowCounter <= 6) {
            boolean isRowEmpty = true;
            for (int i = 0; i < totalColumns; i++) {
                Label lblDay = new Label(String.valueOf(calendarDate.getDayOfMonth()));
                lblDay.getStyleClass().add("calendar-day-cell");
                if (calendarDate.getMonthValue() == yearMonth.getMonthValue()) {
                    lblDay.getStyleClass().add("calendar-day-cell-active");
                    isRowEmpty = false;
                } else {
                    lblDay.getStyleClass().add("calendar-day-cell-inactive");
                }
                calendarGrid.add(lblDay, i, rowCounter);
                calendarDate = calendarDate.plusDays(1);
            }
            if (isRowEmpty && calendarDate.getMonthValue() != yearMonth.getMonthValue()) {
                // Remove the last row if it contains only days of the next month
                if (!calendarGrid.getRowConstraints().isEmpty()) {
                    calendarGrid.getRowConstraints().remove(calendarGrid.getRowConstraints().size() - 1);
                }
                break;
            }
            rowCounter++;
        }
        updateMonthYearDisplay(yearMonth);
    }
}
