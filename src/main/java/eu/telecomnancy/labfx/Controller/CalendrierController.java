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
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAdjusters;
import java.time.DayOfWeek;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


import javafx.scene.input.MouseEvent;
import java.time.temporal.IsoFields;
import javafx.scene.control.Button;


public class CalendrierController {

    @FXML
    private GridPane calendarGrid;
    @FXML
    private Label leftLabel;
    @FXML
    private Label rightLabel;
    @FXML
    private Label viewLabel;

    @FXML
    private Integer weekNumber;
    @FXML
    private Button debugButton;

    @FXML
    private ComboBox<String> leftPicker;
    @FXML
    private ComboBox<Integer> rightPicker;
    @FXML
    private ComboBox<String> viewSelector;

    private LocalDate TODAY;
    private YearMonth currentYearMonth;
    

    @FXML
    private void debugButton(ActionEvent event) {
        System.err.println("currentYearMonth.." + currentYearMonth);
        
        System.err.println("weekNumber........" + weekNumber);
        System.err.println("TODAY............." + TODAY);
        System.err.println("viewSelector......" + viewSelector.getValue());
        System.err.println("viewLabel........." + viewLabel.getText());
        System.err.println("leftPicker........" + leftPicker.getValue());
        System.err.println("leftLabel........." + leftLabel.getText());
        System.err.println("rightPicker......." + rightPicker.getValue());
        System.err.println("rightLabel........" + rightLabel.getText());


    }

    private void debug() {
        System.err.println("currentYearMonth.." + currentYearMonth);
        System.err.println("currentYear......." + currentYearMonth.getYear());
        System.err.println("currentMonth......" + currentYearMonth.getMonthValue());
        System.err.println("weekNumber........" + weekNumber);
        System.err.println("TODAY............." + TODAY);
        System.err.println("viewSelector......" + viewSelector.getValue());
        System.err.println("viewLabel........." + viewLabel.getText());
        System.err.println("leftPicker........" + leftPicker.getValue());
        System.err.println("leftLabel........." + leftLabel.getText());
        System.err.println("rightPicker......." + rightPicker.getValue());
        System.err.println("rightLabel........" + rightLabel.getText());
    }

    @FXML
    private void handleViewSelector(ActionEvent event) {
        String selectedView = viewSelector.getValue();
        if (selectedView != null) {
            viewLabel.setText(selectedView);
            changeView(selectedView);
            System.err.println("_____ViewSelector_____");
            debug();
        } else if (selectedView == null) {
            System.err.println("_____ViewSelector_Null_____");
            debug();
        }
    }
    
    private void changeView(String selectedView) {
        DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern("MMMM", Locale.FRANCE);
        if (selectedView == "Mois") {
            leftLabel.setText(currentYearMonth.format(monthFormatter).substring(0, 1).toUpperCase(Locale.FRANCE) + currentYearMonth.format(monthFormatter).substring(1));
            currentYearMonth = YearMonth.of(rightPicker.getValue(), mapMonthNameToNumber(leftPicker.getValue()));
            // fillCalendar(currentYearMonth);
            initializeLeftPicker();
            System.err.println("_____ChangeView_Month_____");
            debug();
        } else if (selectedView == "Semaine") {
            if (currentYearMonth.getMonth() == YearMonth.now().getMonth()) {
                weekNumber = TODAY.get(ChronoField.ALIGNED_WEEK_OF_YEAR);
            } else {
                weekNumber = getFirstWeekIndexOfMonth(currentYearMonth);;
            }
            leftLabel.setText("Semaine " + weekNumber.toString());
            rightLabel.setText(currentYearMonth.getYear()+"");
            initializeLeftPicker();
            System.err.println("_____ChangeView_Week_____");
            debug();                
        } else if (selectedView == "Jour") {
            // System.err.println("Logique pour afficher la vue quotidienne");
        }
        // switch (selectedView) {
        //     case "Mois":
        //         leftLabel.setText(currentYearMonth.format(monthFormatter).substring(0, 1).toUpperCase(Locale.FRANCE) + currentYearMonth.format(monthFormatter).substring(1));
        //         currentYearMonth = getMonthFromWeek(weekNumber, currentYearMonth.getYear());
        //         fillCalendar(currentYearMonth);
        //         initializeRightPicker();
        //         System.err.println("_____ChangeView_Month_____");
        //         debug();
        //         break;
        //     case "Semaine":
        //         if (currentYearMonth.getMonth() == YearMonth.now().getMonth()) {
        //             weekNumber = TODAY.get(ChronoField.ALIGNED_WEEK_OF_YEAR);
        //         } else {
        //             weekNumber = getFirstWeekIndexOfMonth(currentYearMonth);;
        //         }
        //         leftLabel.setText("Semaine " + weekNumber.toString());
        //         rightLabel.setText(currentYearMonth.getYear()+"");
        //         System.err.println("_____ChangeView_Week_____");
        //         debug();                
        //         break;
        //     case "Jour":
        //         // System.err.println("Logique pour afficher la vue quotidienne");
        //         break;
        // }
    }

    @FXML
    private void showViewSelector() {
        viewSelector.show();
    }

    public void initialize() {
        currentYearMonth = YearMonth.now();
        TODAY = LocalDate.now();
        weekNumber = TODAY.get(ChronoField.ALIGNED_WEEK_OF_YEAR);
        // weeklyYear = TODAY.getYear();
        viewSelector.getItems().addAll("Mois", "Semaine", "Jour");
        viewSelector.setValue("Mois");
        viewLabel.setText("Mois");
        leftLabel.setText("Test");
        fillCalendar(currentYearMonth);
        updateMonthYearDisplay(currentYearMonth);
        changeView("Mois");
        initializeLeftPicker();
        initializeRightPicker();
    }    

    private void fillCalendar(YearMonth yearMonth) {
        // Clear previous calendar entries
    
        
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

    private void initializeLeftPicker() {
        DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern("MMMM", Locale.FRANCE);
        ObservableList<String> months = FXCollections.observableArrayList(
            "Janvier", "Février", "Mars", "Avril", "Mai", "Juin",
            "Juillet", "Août", "Septembre", "Octobre", "Novembre", "Décembre"
        );

        ObservableList<String> weeks = FXCollections.observableArrayList();
        for (int i = 1; i <= 52; i++) {
            String weekNumber = "Semaine " + String.valueOf(i);
            weeks.add(weekNumber);
        }

        leftPicker.getItems().clear(); // Nettoyer les anciennes données avant de remplir


        if (viewSelector.getValue() == "Mois") {
            leftLabel.setText(currentYearMonth.format(monthFormatter).substring(0, 1).toUpperCase(Locale.FRANCE) + currentYearMonth.format(monthFormatter).substring(1));
            leftPicker.setItems(months);
        } else if (viewSelector.getValue() == "Semaine") {
            if (currentYearMonth.getMonth() == YearMonth.now().getMonth()) {
                weekNumber = TODAY.get(ChronoField.ALIGNED_WEEK_OF_YEAR);
            } else {
                weekNumber = getFirstWeekIndexOfMonth(currentYearMonth);;
            }
            leftLabel.setText("Semaine " + weekNumber.toString());
            // // // // rightLabel.setText(currentYearMonth.getYear()+"");
            leftPicker.setItems(weeks);
        } else if (viewSelector.getValue() == "Jour") {
            leftLabel.setText("Jour");
        }
        
        
        leftPicker.setVisible(false); // Le ComboBox est initialement caché
    }

    private void initializeRightPicker() {
        int currentYear = YearMonth.now().getYear();
        rightPicker.getItems().clear(); // Nettoyer les anciennes données avant de remplir
        for (int year = currentYear - 2; year <= currentYear + 5; year++) {
            rightPicker.getItems().add(year);
        }
        rightPicker.setVisible(false);
        rightPicker.setValue(currentYear); // Sélectionnez l'année actuelle par défaut
    }

    @FXML //Montrer le picker gauche lors du clic sur le label
    private void leftLabelAction(MouseEvent event) {
        leftPicker.show(); // Affiche le ComboBox lors du clic sur le label
    }

    @FXML //Montrer le picker droit lors du clic sur le label
    private void rightLabelAction() {
        rightPicker.show(); // Montrer le ComboBox lorsque l'utilisateur clique sur le label
    }


    private void correctWeekNumber(Integer cinquanteDeux) {
        if (cinquanteDeux <= 0) {
            weekNumber = 52;
            currentYearMonth = currentYearMonth.minusYears(1);
            // weeklyYear = currentYearMonth.getYear();
            rightLabel.setText(currentYearMonth.getYear()+"");
        } else if (cinquanteDeux >= 53) {
            weekNumber = 1;
            currentYearMonth = currentYearMonth.plusYears(1);
            // weeklyYear = currentYearMonth.getYear();
            rightLabel.setText(currentYearMonth.getYear()+"");
        }
    }

    @FXML
    private void previousButton() {
        if (viewSelector.getValue() == "Mois") {
            currentYearMonth = currentYearMonth.minusMonths(1);
            fillCalendar(currentYearMonth);
            updateMonthYearDisplay(currentYearMonth);
            System.err.println("_____PreviousButton_Month_____");
            debug();
        } else if (viewSelector.getValue() == "Semaine") {
            weekNumber--;
            correctWeekNumber(weekNumber);
            leftLabel.setText("Semaine " + weekNumber.toString());
            rightLabel.setText(currentYearMonth.getYear()+"");
            System.err.println("_____PreviousButton_Week_____");
            debug();
        } else if (viewLabel.getText() == "Jour") {
            TODAY = TODAY.minusDays(1);
            leftLabel.setText(TODAY.toString());
        }
        
        // fillCalendar(currentYearMonth);
        // updateMonthYearDisplay(currentYearMonth);
    }

    @FXML
    private void currentDate() {
        currentYearMonth = YearMonth.now();
        if (viewSelector.getValue() == "Mois") {
            fillCalendar(currentYearMonth);
            updateMonthYearDisplay(currentYearMonth);
            System.err.println("_____CurrentButton_Month_____");
            debug();
        } else if (viewSelector.getValue() == "Semaine") {
            weekNumber = TODAY.get(ChronoField.ALIGNED_WEEK_OF_YEAR);
            leftPicker.setValue("Semaine " + weekNumber.toString());
            leftLabel.setText("Semaine " + weekNumber.toString());
            rightPicker.setValue(currentYearMonth.getYear());
            rightLabel.setText(currentYearMonth.getYear()+"");
            System.err.println("_____CurrentButton_Week_____");
            debug();
        } else if (viewLabel.getText() == "Jour") {
            TODAY = LocalDate.now();
            leftLabel.setText(TODAY.toString());
        }
    }

    @FXML
    private void nextButton() {
        if (viewSelector.getValue() == "Mois") {
            currentYearMonth = currentYearMonth.plusMonths(1);
            fillCalendar(currentYearMonth);
            updateMonthYearDisplay(currentYearMonth);
            System.err.println("_____NextButton_Month_____");
            debug();
        } else if (viewSelector.getValue() == "Semaine") {
            weekNumber++;
            correctWeekNumber(weekNumber);
            leftLabel.setText("Semaine " + weekNumber.toString());
            rightLabel.setText(currentYearMonth.getYear()+"");
            System.err.println("_____NextButton_Week_____");
            debug();
        } else if (viewLabel.getText() == "Jour") {
            TODAY = TODAY.plusDays(1);
            leftLabel.setText(TODAY.toString());
        }
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
        if (monthName == null) {
            return currentYearMonth.getMonthValue();
        } else {
            return monthNames.getOrDefault(monthName, currentYearMonth.getMonthValue());
        }
    }

    private int mapWeekstrToNumber(String weekStr) {
        Map<String, Integer> weekNumbers = new HashMap<>();
        for (int i = 1; i <= 52; i++) {
            weekNumbers.put("Semaine " + i, i);
        }
        return weekNumbers.getOrDefault(weekStr, weekNumber);
    }

    

    private YearMonth getMonthFromWeek(int weekOfYear, int year) {
        LocalDate date = LocalDate.ofYearDay(year, 1)
                                  .with(IsoFields.WEEK_OF_WEEK_BASED_YEAR, weekOfYear)
                                  .with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        return YearMonth.from(date);
    }

    private int getFirstWeekIndexOfMonth(YearMonth yearMonth) {
        LocalDate firstDayOfMonth = yearMonth.atDay(1);
        return firstDayOfMonth.get(ChronoField.ALIGNED_WEEK_OF_YEAR);
    }
    
        
    
    @FXML
    private void handleLeftPicker(ActionEvent event) {
        if (viewSelector.getValue() == "Mois") {
            String selectedMonthName = leftPicker.getValue();
            if (selectedMonthName != null) {
                int monthNumber = mapMonthNameToNumber(selectedMonthName);
                // Change to selected month while keeping the current year
                YearMonth selectedMonth = YearMonth.of(currentYearMonth.getYear(), monthNumber);
                currentYearMonth = selectedMonth;
                weekNumber = getFirstWeekIndexOfMonth(currentYearMonth);
                fillCalendar(currentYearMonth);
                // changeYearMonth(selectedMonth);
                updateMonthYearDisplay(currentYearMonth);
                System.err.println("_____LeftPicker_Month_____");
                debug();
            }
            } else if (viewSelector.getValue() == "Semaine") {
                String selectedWeekStr = leftPicker.getValue();
                if (selectedWeekStr != null) {
                    weekNumber = mapWeekstrToNumber(selectedWeekStr);
                    leftLabel.setText("Semaine " + weekNumber.toString());
                    currentYearMonth = getMonthFromWeek(weekNumber, currentYearMonth.getYear());
                }
                System.err.println("_____LeftPicker_Week_____");
                debug();
            }
        }

    @FXML
    private void handleRightPicker(ActionEvent event) {
        if (viewSelector.getValue() == "Mois") {
            Integer selectedYear = rightPicker.getValue();
            if (selectedYear != null) {
                // Change to selected year while keeping the current month
                YearMonth selectedYearMonth = YearMonth.of(selectedYear, currentYearMonth.getMonthValue());
                currentYearMonth = selectedYearMonth;
                // weeklyYear = selectedYear;
                fillCalendar(currentYearMonth);
                updateMonthYearDisplay(currentYearMonth);
                System.err.println("_____RightPicker_Month_____");
                debug();
            } else if (selectedYear == null) {
                rightPicker.setValue(currentYearMonth.getYear());
                System.err.println("_____RightPicker_Month_Null_____");
                debug();
            }
        } else if (viewSelector.getValue() == "Semaine") {
            Integer selectedYear = rightPicker.getValue();
            if (selectedYear != null) {
                // Change to selected year while keeping the current week
                currentYearMonth = getMonthFromWeek(weekNumber, selectedYear);
                rightLabel.setText(selectedYear+"");
                System.err.println("_____RightPicker_Week_____");
                debug();
            } else if (selectedYear == null) {
                rightPicker.setValue(currentYearMonth.getYear());
                System.err.println("_____RightPicker_Week_Null_____");
                debug();
            }
            
        }
        
    }

    private void updateMonthYearDisplay(YearMonth yearMonth) {
        DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern("MMMM", Locale.FRANCE);
        DateTimeFormatter yearFormatter = DateTimeFormatter.ofPattern("yyyy", Locale.FRANCE);
        leftLabel.setText(currentYearMonth.format(monthFormatter).substring(0, 1).toUpperCase(Locale.FRANCE) + currentYearMonth.format(monthFormatter).substring(1));
        leftPicker.setValue(currentYearMonth.format(monthFormatter).substring(0, 1).toUpperCase(Locale.FRANCE) + currentYearMonth.format(monthFormatter).substring(1));
        // monthLabel.setText(yearMonth.format(monthFormatter));
        rightLabel.setText(yearMonth.format(yearFormatter));
        rightPicker.setValue(yearMonth.getYear());
    }

    





}
