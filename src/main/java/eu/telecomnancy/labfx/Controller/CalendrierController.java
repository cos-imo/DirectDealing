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
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.Priority;
import javafx.scene.text.TextAlignment;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.time.format.TextStyle;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAdjusters;
import org.joda.time.Days;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.util.ArrayList;
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
import eu.telecomnancy.labfx.EventRessource;
import eu.telecomnancy.labfx.Ressource;




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

    @FXML
    RowConstraints ligneContrainte;

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

    private void debug() {
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
        debug();
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

    private void fillCalendar() throws SQLException {
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
            lblDay.setStyle("-fx-background-color: grey; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 5;-fx-pref-height:40px;");
            calendarGrid.add(lblDay, i, 0); // Ajoutez au GridPane
            GridPane.setHgrow(lblDay, Priority.ALWAYS); // Permettre au Label de croître horizontalement
            GridPane.setFillWidth(lblDay, true); // Le Label occupe toute la largeur de la cellule
        }
    
        while (calendarStart.isBefore(calendarEnd)) {
            for (int j = 0; j < 7; j++) { // Weekday columns
                if (calendrier.getModeAffichage() == ModeAffichage.Semaine){
                    Interval interval = new Interval(calendarStart,calendarStart.plusHours(4).minusSeconds(1));
                    calendarStart = addDayToCalenderAsWeek(interval, j,weekRow-1);
                    if (j==5){
                        break;
                    }
                } else if (calendrier.getModeAffichage() == ModeAffichage.Mois){
                    Interval interval = new Interval(calendarStart,calendarStart.plusDays(1).minusSeconds(1));
                    calendarStart = addDayToCalenderAsMonth(interval, weekRow, j);
                }
            }
            // If the row is completely in the next month, remove it
            if (calendarStart.getMonthOfYear() != calendrier.getBetweenDate().getStart().getMonthOfYear() && calendarStart.getDayOfWeek() == 1) {
                break;
            }
            weekRow++;
        }
    }
    private DateTime addDayToCalenderAsMonth(Interval interval, int weekRow, int dayColumn) throws SQLException {
        DateTime calendarStart = interval.getStart();

        Label dayLabel = new Label(String.valueOf(calendarStart.getDayOfMonth()));
        dayLabel.setAlignment(Pos.TOP_LEFT);
        dayLabel.setContentDisplay(ContentDisplay.TOP);
        dayLabel.setGraphicTextGap(0.0);
        dayLabel.setStyle("-fx-padding: 5;");

        // Add the Label to a VBox
        VBox dayBox = new VBox();
        dayBox.getChildren().add(dayLabel);
        dayBox.setAlignment(Pos.TOP_LEFT);
        dayBox.setMaxWidth(Double.MAX_VALUE);
        dayBox.setStyle("-fx-border-color: black; -fx-pref-width: 400px; -fx-pref-height: 400px; -fx-padding:3px;");
        dayBox.setOnMouseClicked((event) -> {
        targetDate = calendarStart;
        calendrier.setModeAffichage(ModeAffichage.Semaine);
        viewLabel.setText("Semaine");
        viewSelector.setValue("Semaine");
        try {
            initializeBis();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        });

        VBox eventBox = new VBox();
        eventBox.setAlignment(Pos.CENTER);
        eventBox.setMaxWidth(350);
        eventBox.setSpacing(3);
        dayBox.getChildren().add(eventBox);
        ArrayList<EventRessource> todayEvent = getTodayEvent(interval);
        ArrayList<Ressource> todayRessource = getTodayRessource(todayEvent,interval);
        for (EventRessource event : todayEvent) {
            Label eventLabel = new Label(event.getRessource().getName());
            eventLabel.setAlignment(Pos.TOP_LEFT);
            eventLabel.setContentDisplay(ContentDisplay.TOP);
            eventLabel.setGraphicTextGap(0.0);
            eventLabel.setStyle("-fx-text-fill: black;-fx-background-color: #f7921a; -fx-pref-width: 350px; -fx-pref-height: 5px;-fx-background-radius:10px;");
            eventLabel.setMaxHeight(5);
            eventBox.getChildren().add(eventLabel);
        }
        for (Ressource ressource : todayRessource) {
            Label ressourceLabel = new Label(ressource.getName());
            ressourceLabel.setAlignment(Pos.TOP_LEFT);
            ressourceLabel.setContentDisplay(ContentDisplay.TOP);
            ressourceLabel.setGraphicTextGap(0.0);
            ressourceLabel.setStyle("-fx-text-fill: black;-fx-background-color: #c1adff; -fx-pref-width: 350px; -fx-pref-height: 5px;-fx-background-radius:10px;");
            ressourceLabel.setMaxHeight(5);
            eventBox.getChildren().add(ressourceLabel);
        }

        // Label dayLabel = new Label(String.valueOf(calendarStart.getDayOfMonth()));
        // dayLabel.setAlignment(Pos.TOP_LEFT); // Aligner le texte en haut à gauche
        // dayLabel.setContentDisplay(ContentDisplay.TOP); // Positionner le texte au-dessus de tout autre contenu graphique
        // dayLabel.setGraphicTextGap(0.0); // Pas d'écart entre le texte et le graphique (si vous en utilisez un)
        // dayLabel.setStyle("-fx-border-color: black; -fx-padding: 5; -fx-pref-width: 400px; -fx-pref-height: 400px;");
        if (DateTime.now().getDayOfYear() == calendarStart.getDayOfYear() && DateTime.now().getYear() == calendarStart.getYear()) {
            dayBox.setStyle(dayBox.getStyle() + "-fx-background-color: #d7c162;");
        } else {
            dayBox.setStyle(dayBox.getStyle() + "-fx-background-color: white;");
        }
        if(!calendrier.getBetweenDate().contains(calendarStart)) {
            dayLabel.setStyle(dayLabel.getStyle() + "-fx-text-fill: lightgrey;");
        }
        calendarGrid.add(dayBox, dayColumn, weekRow);
        return calendarStart.plusDays(1);
    }
    private DateTime addDayToCalenderAsWeek(Interval interval, int dayColumn,int weekRow) throws SQLException{
        DateTime calendarStart = interval.getStart();
        
        Label dayLabel = new Label(interval.getStart().getHourOfDay() + "h" + interval.getStart().getMinuteOfHour() + " - " + interval.getEnd().getHourOfDay() + "h" + interval.getEnd().getMinuteOfHour());
        dayLabel.setAlignment(Pos.TOP_LEFT);
        dayLabel.setContentDisplay(ContentDisplay.TOP);
        dayLabel.setGraphicTextGap(0.0);
        dayLabel.setStyle("-fx-padding: 5;");

        // Add the Label to a VBox
        VBox dayBox = new VBox();
        dayBox.getChildren().add(dayLabel);
        dayBox.setAlignment(Pos.TOP_LEFT);
        dayBox.setMaxWidth(Double.MAX_VALUE);
        dayBox.setStyle("-fx-border-color: black; -fx-pref-width: 400px; -fx-pref-height: 600px; -fx-padding:3px;");
        dayBox.setOnMouseClicked((event) -> {
        targetDate = calendarStart;
        calendrier.setModeAffichage(ModeAffichage.Mois);
        viewLabel.setText("Mois");
        viewSelector.setValue("Mois");
        try {
            initializeBis();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        });

        VBox eventBox = new VBox();
        eventBox.setAlignment(Pos.CENTER);
        eventBox.setMaxWidth(350);
        eventBox.setSpacing(3);
        dayBox.getChildren().add(eventBox);

        ArrayList<EventRessource> todayEvent = getTodayEvent(interval);
        ArrayList<Ressource> todayRessource = getTodayRessource(todayEvent,interval);
        for (EventRessource event : todayEvent) {
            Label eventLabel = new Label(event.getRessource().getName());
            eventLabel.setAlignment(Pos.TOP_LEFT);
            eventLabel.setContentDisplay(ContentDisplay.TOP);
            eventLabel.setGraphicTextGap(0.0);
            eventLabel.setStyle("-fx-text-fill: black;-fx-background-color: #f7921a; -fx-pref-width: 350px; -fx-pref-height: 5px;-fx-background-radius:10px;");
            eventLabel.setMaxHeight(5);
            eventBox.getChildren().add(eventLabel);
        }
        for (Ressource ressource : todayRessource) {
            Label ressourceLabel = new Label(ressource.getName());
            ressourceLabel.setAlignment(Pos.TOP_LEFT);
            ressourceLabel.setContentDisplay(ContentDisplay.TOP);
            ressourceLabel.setGraphicTextGap(0.0);
            ressourceLabel.setStyle("-fx-text-fill: black;-fx-background-color: #c1adff; -fx-pref-width: 350px; -fx-pref-height: 5px;-fx-background-radius:10px;");
            ressourceLabel.setMaxHeight(5);
            eventBox.getChildren().add(ressourceLabel);
        }          
        if (DateTime.now().getDayOfYear() == calendarStart.getDayOfYear() && DateTime.now().getYear() == calendarStart.getYear()) {
            dayBox.setStyle(dayBox.getStyle() + "-fx-background-color: #d7c162;");
        } else {
            dayBox.setStyle(dayBox.getStyle() + "-fx-background-color: white;");
        }
        if(!calendrier.getBetweenDate().contains(calendarStart)) {
            dayLabel.setStyle(dayLabel.getStyle() + "-fx-text-fill: lightgrey;");
        }
        calendarGrid.add(dayBox, weekRow, dayColumn+1);
        return calendarStart.plusHours(4);
    }

    private ArrayList<EventRessource> getTodayEvent(Interval thisDay) throws SQLException {
        ArrayList<EventRessource> eventActif = new ArrayList<EventRessource>();
        for (EventRessource event : calendrier.getEventActif()) {
            if (thisDay.contains(event.getDateDebut()) || thisDay.contains(event.getDateFin()) || event.getDateDebut().isBefore(thisDay.getStart()) && event.getDateFin().isAfter(thisDay.getEnd())) {
                EventRessource newEvent = EventReducedToAffichage(event, thisDay);

                eventActif.add(newEvent);
            }
        }
        return eventActif;
    }
    private ArrayList<Ressource> getTodayRessource(ArrayList<EventRessource> todayEvent, Interval thisDay) throws SQLException {
        ArrayList<Ressource> ressourcesActifs = new ArrayList<Ressource>();
        for (Ressource ressource : calendrier.getRessourceActif()) {
            if (thisDay.contains(ressource.getDateDebut()) || thisDay.contains(ressource.getDateFin()) || ressource.getDateDebut().isBefore(thisDay.getStart()) && ressource.getDateFin().isAfter(thisDay.getEnd())) {
                boolean alreadyHereAsEvent = false;
                for (EventRessource event : todayEvent){
                    if (event.getRessource().getId() == ressource.getId()){
                        alreadyHereAsEvent = true;
                        break;
                    }
                }
                if (alreadyHereAsEvent == false){
                    ressourcesActifs.add(ressourceReducedToAffichage(ressource, thisDay));
                }
            }
        }
        return ressourcesActifs;
    }


    public DateTime reduceDebutDate(DateTime date1,Interval betweenDate){
        return Calendrier.maxDate(date1, betweenDate.getStart());
    }
    public DateTime reduceFinDate(DateTime date1,Interval betweenDate){
        return Calendrier.minDate(date1, betweenDate.getEnd());
    }
    public Ressource ressourceReducedToAffichage(Ressource r,Interval betweenDate){
        Ressource newRessource = new Ressource(r,reduceDebutDate(r.getDateDebut(),betweenDate),reduceFinDate(r.getDateFin(),betweenDate));
        return newRessource;
    }
    public EventRessource EventReducedToAffichage(EventRessource r,Interval betweenDate){
        EventRessource newEvent = new EventRessource(r,reduceDebutDate(r.getDateDebut(),betweenDate),reduceFinDate(r.getDateFin(),betweenDate));
        return newEvent;
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
            if (targetDate.getMonthOfYear() != 1 || targetDate.getYear() != 2024) {
                targetDate = targetDate.minusMonths(1);
            }
        } else if (calendrier.getModeAffichage() == ModeAffichage.Semaine) {
            if (targetDate.getWeekOfWeekyear() != 1 || targetDate.getYear() != 2024) {
                targetDate = targetDate.minusWeeks(1);
            }
        } else if (calendrier.getModeAffichage() == ModeAffichage.Jour) {
            if (targetDate.getDayOfYear() != 1 || targetDate.getYear() != 2024) {
                targetDate = targetDate.minusDays(1);
            }
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
        
        if (calendrier.getModeAffichage() == ModeAffichage.Mois) {
            int actualweek = targetDate.getWeekOfWeekyear();
            int targetweek = leftPicker.getItems().indexOf(selectedMonth)+1;
            targetDate = targetDate.plusWeeks(targetweek - actualweek);
            initializeBis();

        } else if (calendrier.getModeAffichage() == ModeAffichage.Semaine) {
            targetDate = targetDate.withWeekOfWeekyear(leftPicker.getItems().indexOf(selectedMonth)+1);
            initializeBis();
        }
        leftLabel.setText(selectedMonth);
        leftPicker.setValue(selectedMonth);
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
