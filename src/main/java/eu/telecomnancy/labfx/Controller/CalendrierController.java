package eu.telecomnancy.labfx.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.text.TextAlignment;

import java.time.YearMonth;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.time.DayOfWeek;
import java.util.Locale;
import java.util.Map;



import javafx.scene.input.MouseEvent;

public class CalendrierController {

    @FXML
    private GridPane calendarGrid;
    @FXML
    private Label monthLabel;
    @FXML
    private Label yearLabel;
    @FXML
    private Label viewLabel;
    @FXML
    private Integer weekNumber;
    @FXML
    private ComboBox<String> monthPicker;
    @FXML
    private ComboBox<Integer> yearPicker;
    @FXML
    private ComboBox<String> viewSelector;

    private YearMonth currentYearMonth;
    private String lastSelectedView = "Mois";



    @FXML
    private void previousMonth() {
        currentYearMonth = currentYearMonth.minusMonths(1);
        
        fillCalendar(currentYearMonth);
        updateMonthYearDisplay(currentYearMonth);
    }

    @FXML
    private void currentMonth() {
        currentYearMonth = YearMonth.now();
        fillCalendar(currentYearMonth);
        updateMonthYearDisplay(currentYearMonth);
    }

    @FXML
    private void nextMonth() {
        currentYearMonth = currentYearMonth.plusMonths(1);
        fillCalendar(currentYearMonth);
        updateMonthYearDisplay(currentYearMonth);
    }

    @FXML
    private void handleViewSelector(ActionEvent event) {
        String selectedView = viewSelector.getValue();
        if (selectedView != null) {
            viewLabel.setText(selectedView);
            lastSelectedView = selectedView; // Mise à jour de la variable globale
            changeView(selectedView);
        }
    }
    
    private void changeView(String selectedView) {
        lastSelectedView = selectedView;
        switch (selectedView) {
            case "Mois":
                System.err.println("Logique pour afficher la vue mensuelle");
                fillCalendar(currentYearMonth);
                

                break;
            case "Semaine":
                System.err.println("Logique pour afficher la vue hebdomadaire");
                break;
            case "Jour":
                System.err.println("Logique pour afficher la vue quotidienne");
                break;
        }
    }




    

    public void initialize() {
        currentYearMonth = YearMonth.now();
        viewSelector.getItems().addAll("Mois", "Semaine", "Jour");
        viewLabel.setText(lastSelectedView);
        fillCalendar(currentYearMonth);
        updateMonthYearDisplay(currentYearMonth);
        changeView(lastSelectedView);
        initializeMonthPicker();
        initializeYearPicker();
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
            lblDay.setAlignment(Pos.CENTER); // Centrer le texte dans le Label
            lblDay.setMaxWidth(Double.MAX_VALUE); // Permettre au Label de s'étendre à la largeur maximale
            lblDay.setStyle("-fx-background-color: grey; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 5;");
            calendarGrid.add(lblDay, i, 0); // Ajoutez au GridPane
            GridPane.setHgrow(lblDay, Priority.ALWAYS); // Permettre au Label de croître horizontalement
            GridPane.setFillWidth(lblDay, true); // Le Label occupe toute la largeur de la cellule


        
}

    while (date.getMonthValue() <= yearMonth.getMonthValue() | currentYearMonth.getMonthValue() == YearMonth.now().getMonthValue()) {
        for (int j = 0; j < 7; j++) { // Weekday columns
            Label dayLabel = new Label(String.valueOf(date.getDayOfMonth()));
            dayLabel.setAlignment(Pos.TOP_LEFT); // Aligner le texte en haut à gauche
            dayLabel.setContentDisplay(ContentDisplay.TOP); // Positionner le texte au-dessus de tout autre contenu graphique
            dayLabel.setGraphicTextGap(0.0); // Pas d'écart entre le texte et le graphique (si vous en utilisez un)
            dayLabel.setStyle("-fx-border-color: black; -fx-background-color: white; -fx-padding: 5; -fx-pref-width: 400px; -fx-pref-height: 300px;");            
            if(date.getMonthValue() != yearMonth.getMonthValue()) {
                dayLabel.setStyle(dayLabel.getStyle() + "-fx-text-fill: lightgrey;");
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

    private void initializeMonthPicker() {
        ObservableList<String> months = FXCollections.observableArrayList(
            "Janvier", "Février", "Mars", "Avril", "Mai", "Juin",
            "Juillet", "Août", "Septembre", "Octobre", "Novembre", "Décembre"
        );
        monthPicker.setItems(months);
        monthPicker.setVisible(false); // Le ComboBox est initialement caché
    }

    private void initializeYearPicker() {
        int currentYear = YearMonth.now().getYear();
        yearPicker.getItems().clear(); // Nettoyer les anciennes données avant de remplir
        for (int year = currentYear - 5; year <= currentYear + 5; year++) {
            yearPicker.getItems().add(year);
        }
        yearPicker.setVisible(false);
        yearPicker.setValue(currentYear); // Sélectionnez l'année actuelle par défaut
    }

    @FXML
    private void showMonthPicker(MouseEvent event) {
        monthPicker.show(); // Affiche le ComboBox lors du clic sur le label
    }

    @FXML
    private void showYearPicker() {
        yearPicker.show(); // Montrer le ComboBox lorsque l'utilisateur clique sur le label
    }

    @FXML
    private void showViewSelector() {
        viewSelector.show();
    }
    

    private int mapMonthNameToNumber(String monthName) {
        Map<String, Integer> monthNames = Map.ofEntries(
            Map.entry("Janvier", 1),
            Map.entry("Février", 2),
            Map.entry("Mars", 3),
            Map.entry("Avril", 4),
            Map.entry("Mai", 5),
            Map.entry("Juin", 6),
            Map.entry("Juillet", 7),
            Map.entry("Août", 8),
            Map.entry("Septembre", 9),
            Map.entry("Octobre", 10),
            Map.entry("Novembre", 11),
            Map.entry("Décembre", 12)
        );
        return monthNames.getOrDefault(monthName, currentYearMonth.getMonthValue());
    }
    
    @FXML
    private void handleMonthPicker(ActionEvent event) {
        String selectedMonthName = monthPicker.getValue();
        if (selectedMonthName != null) {
            int monthNumber = mapMonthNameToNumber(selectedMonthName);
            // Change to selected month while keeping the current year
            YearMonth selectedMonth = YearMonth.of(currentYearMonth.getYear(), monthNumber);
            currentYearMonth = selectedMonth;
            fillCalendar(currentYearMonth);
            // changeYearMonth(selectedMonth);
            updateMonthYearDisplay(currentYearMonth);
        }
    }

    @FXML
    private void handleYearPicker(ActionEvent event) {
        Integer selectedYear = yearPicker.getValue();
        if (selectedYear != null) {
            // Change to selected year while keeping the current month
            currentYearMonth = YearMonth.of(selectedYear, currentYearMonth.getMonthValue());
            YearMonth selectedYearMonth = YearMonth.of(selectedYear, currentYearMonth.getMonthValue());
            currentYearMonth = selectedYearMonth;
            fillCalendar(currentYearMonth);
            updateMonthYearDisplay(currentYearMonth);
        }
    }




    private void updateMonthYearDisplay(YearMonth yearMonth) {
        DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern("MMMM", Locale.FRANCE);
        DateTimeFormatter yearFormatter = DateTimeFormatter.ofPattern("yyyy", Locale.FRANCE);
        monthLabel.setText(yearMonth.format(monthFormatter).substring(0, 1).toUpperCase(Locale.FRANCE) + yearMonth.format(monthFormatter).substring(1));
        // monthLabel.setText(yearMonth.format(monthFormatter));
        yearLabel.setText(yearMonth.format(yearFormatter));
    }
}
