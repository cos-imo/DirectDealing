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

import java.time.format.TextStyle;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAdjusters;
import org.joda.time.Days;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

//DateTime joda Time
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import javafx.scene.input.MouseEvent;
import java.time.temporal.IsoFields;
import javafx.scene.control.Button;

import eu.telecomnancy.labfx.Calendrier;
import eu.telecomnancy.labfx.ModeAffichage;
import eu.telecomnancy.labfx.Session;


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

    private Calendrier calendrier;
    private DateTime targetDate;

    @FXML
    private void debugButton(ActionEvent event) {
        System.err.println("currentYearMonth.." + targetDate.getMonthOfYear());
        
        System.err.println("weekNumber........" + targetDate.getWeekOfWeekyear());
        System.err.println("TODAY............." + targetDate);
        System.err.println("viewSelector......" + viewSelector.getValue());
        System.err.println("viewLabel........." + viewLabel.getText());
        System.err.println("leftPicker........" + leftPicker.getValue());
        System.err.println("leftLabel........." + leftLabel.getText());
        System.err.println("rightPicker......." + rightPicker.getValue());
        System.err.println("rightLabel........" + rightLabel.getText());


    }
    @FXML
    private void handleViewSelector(ActionEvent event) throws SQLException {
        String selectedView = viewSelector.getValue();
        if ("Mois".equals(selectedView)) {
            calendrier.setModeAffichage(ModeAffichage.Mois);
        } else if (selectedView.equals("Semaine")) {
            calendrier.setModeAffichage(ModeAffichage.Semaine);
        }
        viewLabel.setText(selectedView);
        viewSelector.setValue(selectedView);
        initializeBis();
    }

    @FXML
    private void showViewSelector() {
        viewSelector.show();
    }

    public void initialize() throws SQLException {
        targetDate = DateTime.now();
        viewLabel.setText("Mois");
        viewSelector.getItems().addAll("Mois", "Semaine");
        weekNumber = targetDate.getWeekOfWeekyear();
        // weeklyYear = TODAY.getYear();
        viewSelector.setValue("Mois");
        initializeBis();
    }    
    public void initializeBis() throws SQLException {
        calendarGrid.getChildren().clear();
        if (calendrier != null) {
            this.calendrier = new Calendrier(Session.getInstance().getCurrentUser(), targetDate, calendrier.getModeAffichage()); //Dedans on a toutes les informations à affiche
        }
        else {
            this.calendrier = new Calendrier(Session.getInstance().getCurrentUser(), targetDate, ModeAffichage.Mois); //Dedans on a toutes les informations à afficher
        }
        initializeLeftPicker();
        initializeRightPicker();
        fillCalendar();
    }

    private void fillCalendar() {
        // Clear previous calendar entries
        // Determine the starting day for the calendar
        DateTime calendarStart = calendrier.getBetweenDate().getStart();
        DateTime calendarEnd = calendrier.getBetweenDate().getEnd();
        org.joda.time.LocalDate debutMois = null;
        org.joda.time.LocalDate finMois = null;
        if (calendrier.getModeAffichage() == ModeAffichage.Mois) {
            debutMois = calendarStart.toLocalDate().withDayOfMonth(1);
            finMois = calendarStart.toLocalDate().dayOfMonth().withMaximumValue();
        } else if (calendrier.getModeAffichage() == ModeAffichage.Semaine) {
            debutMois = calendarStart.toLocalDate().withDayOfWeek(1);
            finMois = calendarStart.toLocalDate().dayOfWeek().withMaximumValue();
        }
        int joursOutDebut = debutMois.getDayOfWeek() - 1;

        int joursOutFin =  7 - finMois.getDayOfWeek();

        calendarStart= calendarStart.minusDays(joursOutDebut);
        calendarEnd = calendarEnd.plusDays(joursOutFin);
        
        // Fill the calendar with days
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
    
        while (calendarStart.isBefore(calendarEnd)) {
            for (int j = 0; j < 7; j++) { // Weekday columns
                if (calendrier.getModeAffichage() == ModeAffichage.Semaine){
                    calendarStart = addDayToCalenderAsWeek(calendarStart, j);
                } else if (calendrier.getModeAffichage() == ModeAffichage.Mois){
                    calendarStart = addDayToCalenderAsMonth(calendarStart, weekRow, j);
                }
            }
            // If the row is completely in the next month, remove it
            if (calendarStart.getMonthOfYear() != calendrier.getBetweenDate().getStart().getMonthOfYear() && calendarStart.getDayOfWeek() == 1) {
                break;
            }
            weekRow++;
        }
    }
    private DateTime addDayToCalenderAsMonth(DateTime calendarStart, int weekRow, int dayColumn) {
        Label dayLabel = new Label(String.valueOf(calendarStart.getDayOfMonth()));
        dayLabel.setAlignment(Pos.TOP_LEFT); // Aligner le texte en haut à gauche
        dayLabel.setContentDisplay(ContentDisplay.TOP); // Positionner le texte au-dessus de tout autre contenu graphique
        dayLabel.setGraphicTextGap(0.0); // Pas d'écart entre le texte et le graphique (si vous en utilisez un)
        dayLabel.setStyle("-fx-border-color: black; -fx-padding: 5; -fx-pref-width: 400px; -fx-pref-height: 400px;");
        if (DateTime.now().getDayOfYear() == calendarStart.getDayOfYear() && DateTime.now().getYear() == calendarStart.getYear()) {
            dayLabel.setStyle(dayLabel.getStyle() + "-fx-background-color: #d7c162;");
        } else {
            dayLabel.setStyle(dayLabel.getStyle() + "-fx-background-color: white;");
        }
        if(!calendrier.getBetweenDate().contains(calendarStart)) {
            dayLabel.setStyle(dayLabel.getStyle() + "-fx-text-fill: lightgrey;");
        }
        calendarGrid.add(dayLabel, dayColumn, weekRow);
        return calendarStart.plusDays(1);
    }
    private DateTime addDayToCalenderAsWeek(DateTime calendarStart, int dayColumn){
        Label dayLabel = new Label(String.valueOf(calendarStart.getDayOfMonth()));
        dayLabel.setAlignment(Pos.TOP_LEFT); // Aligner le texte en haut à gauche
        dayLabel.setContentDisplay(ContentDisplay.TOP); // Positionner le texte au-dessus de tout autre contenu graphique
        dayLabel.setGraphicTextGap(0.0); // Pas d'écart entre le texte et le graphique (si vous en utilisez un)
        dayLabel.setStyle("-fx-border-color: black; -fx-padding: 5; -fx-pref-width: 400px; -fx-pref-height: 400px;");            
        if (DateTime.now().getDayOfYear() == calendarStart.getDayOfYear() && DateTime.now().getYear() == calendarStart.getYear()) {
            dayLabel.setStyle(dayLabel.getStyle() + "-fx-background-color: #d7c162;");
        } else {
            dayLabel.setStyle(dayLabel.getStyle() + "-fx-background-color: white;");
        }
        if(targetDate.getMonthOfYear() != calendarStart.getMonthOfYear()) {
            dayLabel.setStyle(dayLabel.getStyle() + "-fx-text-fill: lightgrey;");
        } else {
            dayLabel.setStyle(dayLabel.getStyle() + "-fx-text-fill: black;");
        }
        calendarGrid.add(dayLabel, dayColumn, 1);
        return calendarStart.plusDays(1);
    }
    private void initializeLeftPicker() {
        int currentYearMonth = targetDate.getMonthOfYear();
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

        if (calendrier.getModeAffichage() == ModeAffichage.Mois) {
            leftLabel.setText(months.get(currentYearMonth - 1));
            leftPicker.setItems(months);
        } else if (calendrier.getModeAffichage() == ModeAffichage.Semaine) {
            leftLabel.setText("Semaine " + targetDate.getWeekOfWeekyear());
            leftPicker.setItems(weeks);
        } else if (viewSelector.getValue() == "Jour") {
            leftLabel.setText("Jour");
        }
        
        leftPicker.setVisible(false); // Le ComboBox est initialement caché
    }

    private void initializeRightPicker() {
        int currentYear = targetDate.getYear();
        rightPicker.getItems().clear(); // Nettoyer les anciennes données avant de remplir
        for (int year = Math.max(currentYear-5,2024); year <= currentYear + 5; year++) {
            rightPicker.getItems().add(year);
        }
        // rightPicker.setVisible(false);
        rightLabel.setText(""+currentYear);
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

    @FXML
    private void previousButton() throws SQLException {
        if (calendrier.getModeAffichage() == ModeAffichage.Mois) {
            targetDate = targetDate.minusMonths(1);
        } else if (calendrier.getModeAffichage() == ModeAffichage.Semaine) {
            targetDate = targetDate.minusWeeks(1);
        } else if (calendrier.getModeAffichage() == ModeAffichage.Jour) {
            // Pas de vue jour pour le moment
        }
        initializeBis();
        
        // fillCalendar(currentYearMonth);
        // updateMonthYearDisplay(currentYearMonth);
    }

    @FXML
    private void currentDate() throws SQLException {
        targetDate = DateTime.now();
        initializeBis();
    }

    @FXML
    private void nextButton() throws SQLException {
        if (viewSelector.getValue() == "Mois") {
            targetDate = targetDate.plusMonths(1);
        } else if (viewSelector.getValue() == "Semaine") {
            targetDate = targetDate.plusWeeks(1);
        } else if (viewLabel.getText() == "Jour") {
            // Pas de vue jour pour le moment
        }
        initializeBis();
    }     
    
    @FXML
    private void handleLeftPicker(ActionEvent event) throws SQLException {
        String selectedMonth = leftPicker.getValue();
        leftLabel.setText(selectedMonth);
        leftPicker.setValue(selectedMonth);
        if (calendrier.getModeAffichage() == ModeAffichage.Mois) {
            targetDate = targetDate.withMonthOfYear(leftPicker.getItems().indexOf(selectedMonth) + 1);
            initializeBis();

        } else if (calendrier.getModeAffichage() == ModeAffichage.Semaine) {
            targetDate = targetDate.withWeekOfWeekyear(leftPicker.getItems().indexOf(selectedMonth));
            initializeBis();
        }
    }

    @FXML
    private void handleRightPicker(ActionEvent event) throws SQLException {
        int selectedYear = rightPicker.getValue();
        rightLabel.setText(String.valueOf(selectedYear));
        rightPicker.setValue(selectedYear);
        if (selectedYear != targetDate.getYear()) {
            targetDate = targetDate.plusYears(selectedYear-targetDate.getYear());
            initializeBis();
        }
    }
}
