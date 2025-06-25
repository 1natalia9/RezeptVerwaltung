package com.example.rezeptverwaltung;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Die Klasse {@code RecipeView} stellt die Benutzeroberfläche zur Verwaltung von Rezepten dar.
 * Sie ermöglicht das Erstellen, Bearbeiten, Löschen, Anzeigen und Suchen von Rezepten.
 */
public class RecipeView extends GridPane {

    private final TextField nameField;
    private final TextField ingredientsField;
    private final TextArea instructionsArea;
    private final TextField timeField;
    private final TextField difficultyField;

    private final TableView<Recipe> tableView;
    private final ComboBox<String> categoryBox;

    private final ImageView recipeImageView;
    private File selectedImageFile;

    /**
     * Konstruktor für die Rezeptansicht.
     * Initialisiert alle UI-Komponenten und deren Layout.
     */
    public RecipeView() {
        setPadding(new Insets(10));
        setVgap(10);
        setHgap(10);

        // Eingabefelder
        nameField = new TextField();
        ingredientsField = new TextField();
        instructionsArea = new TextArea();
        timeField = new TextField();
        difficultyField = new TextField();
        categoryBox = new ComboBox<>();

        instructionsArea.setPrefRowCount(4);

        // Layout der Eingabefelder
        add(new Label("Name:"), 0, 0);
        add(nameField, 1, 0);

        add(new Label("Zutaten:"), 0, 1);
        add(ingredientsField, 1, 1);

        add(new Label("Anleitung:"), 0, 2);
        add(instructionsArea, 1, 2);

        add(new Label("Zeit (min):"), 0, 3);
        add(timeField, 1, 3);

        add(new Label("Schwierigkeit (1–5):"), 0, 4);
        add(difficultyField, 1, 4);

        add(new Label("Kategorie:"), 0, 5);
        add(categoryBox, 1, 5);

        categoryBox.getItems().addAll("Frühstück", "Mittagessen", "Abendessen", "Dessert", "Snack");
        categoryBox.setPromptText("Kategorie wählen");

        // Buttons
        Button speichernBtn = new Button("Speichern");
        Button anzeigenBtn = new Button("Liste aktualisieren");
        Button bearbeitenBtn = new Button("Bearbeiten");
        Button loeschenBtn = new Button("Löschen");

        add(speichernBtn, 0, 6);
        add(anzeigenBtn, 1, 6);
        add(bearbeitenBtn, 0, 7);
        add(loeschenBtn, 1, 7);

        // Tabelle zur Anzeige der Rezepte
        tableView = new TableView<>();

        TableColumn<Recipe, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Recipe, String> ingredientsCol = new TableColumn<>("Zutaten");
        ingredientsCol.setCellValueFactory(new PropertyValueFactory<>("ingredients"));

        TableColumn<Recipe, Integer> timeCol = new TableColumn<>("Zeit (min)");
        timeCol.setCellValueFactory(new PropertyValueFactory<>("time"));

        TableColumn<Recipe, Integer> difficultyCol = new TableColumn<>("Schwierigkeit");
        difficultyCol.setCellValueFactory(new PropertyValueFactory<>("difficulty"));

        TableColumn<Recipe, String> categoryCol = new TableColumn<>("Kategorie");
        categoryCol.setCellValueFactory(new PropertyValueFactory<>("category"));

        tableView.getColumns().addAll(nameCol, ingredientsCol, timeCol, difficultyCol, categoryCol);
        tableView.setPrefHeight(200);

        add(tableView, 0, 8, 2, 1);

        // Event-Handler
        speichernBtn.setOnAction(e -> saveRecipe());
        anzeigenBtn.setOnAction(e -> refreshTable());
        bearbeitenBtn.setOnAction(e -> editRecipe());
        loeschenBtn.setOnAction(e -> deleteRecipe());

        // Bildanzeige
        recipeImageView = new ImageView();
        recipeImageView.setFitWidth(200);
        recipeImageView.setPreserveRatio(true);
        add(recipeImageView, 2, 0, 1, 5);

        Button bildAuswaehlenBtn = new Button("Bild auswählen");
        add(bildAuswaehlenBtn, 2, 5);

        bildAuswaehlenBtn.setOnAction(e -> chooseImage());

        // Suchfeld
        TextField searchField = new TextField();
        searchField.setPromptText("Suche nach Name oder Kategorie");
        add(searchField, 0, 9, 2, 1);

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            searchRecipes(newValue);
        });



        // Tabelle: Rezeptauswahl → Felder befüllen + Bild anzeigen
        tableView.setOnMouseClicked(event -> {
            Recipe selected = tableView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                nameField.setText(selected.getName());
                ingredientsField.setText(selected.getIngredients());
                instructionsArea.setText(selected.getInstructions());
                timeField.setText(String.valueOf(selected.getTime()));
                difficultyField.setText(String.valueOf(selected.getDifficulty()));
                categoryBox.setValue(selected.getCategory());
                if (selected.getImagePath() != null) {
                    recipeImageView.setImage(new Image(new File(selected.getImagePath()).toURI().toString()));
                } else {
                    recipeImageView.setImage(null);
                }
            }
        });
        // Tabelle initial befüllen
        refreshTable();
    }

    /**
     * Öffnet einen Dateiauswahldialog, um ein Bild für das Rezept auszuwählen.
     * Das Bild wird anschließend in der Vorschau angezeigt.
     */
    private void chooseImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Bild auswählen");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Bilddateien", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );
        File file = fileChooser.showOpenDialog(getScene().getWindow());
        if (file != null) {
            selectedImageFile = file;
            recipeImageView.setImage(new Image(file.toURI().toString()));
        }
    }


    /**
     * Filtert die Rezeptliste basierend auf dem Suchbegriff (Name oder Kategorie).
     *
     * @param query Der Suchtext
     */
    private void searchRecipes(String query) {
        List<Recipe> allRecipes = DatabaseHelper.getAllRecipes();
        List<Recipe> filtered = allRecipes.stream()
                .filter(r -> r.getName().toLowerCase().contains(query.toLowerCase()) ||
                        r.getCategory().toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());

        tableView.setItems(FXCollections.observableArrayList(filtered));
    }

    /**
     * Speichert ein neues Rezept in der Datenbank, sofern alle Felder korrekt ausgefüllt sind.
     */
    private void saveRecipe() {
        try {
            String name = nameField.getText().trim();
            String ingredients = ingredientsField.getText().trim();
            String instructions = instructionsArea.getText().trim();
            int time = Integer.parseInt(timeField.getText().trim());
            int difficulty = Integer.parseInt(difficultyField.getText().trim());
            String category = categoryBox.getValue();
            String imagePath = selectedImageFile != null ? selectedImageFile.getAbsolutePath() : null;

            if (name.isEmpty() || ingredients.isEmpty() || instructions.isEmpty() || category == null || category.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Eingabefehler");
                alert.setHeaderText(null);
                alert.setContentText("Bitte alle Felder ausfüllen.");
                alert.showAndWait();

                return;
            }

            DatabaseHelper.insertRecipe(name, ingredients, instructions, time, difficulty, category, imagePath);
            clearFields();
            refreshTable();

        } catch (NumberFormatException e) {
            System.out.println("Bitte gültige Zahlen für Zeit und Schwierigkeit eingeben.");
        }
    }

    /**
     * Aktualisiert ein bestehendes Rezept mit den aktuellen Eingabefeldern.
     */
    private void editRecipe() {
        Recipe selected = tableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            try {
                String name = nameField.getText().trim();
                String ingredients = ingredientsField.getText().trim();
                String instructions = instructionsArea.getText().trim();
                int time = Integer.parseInt(timeField.getText().trim());
                int difficulty = Integer.parseInt(difficultyField.getText().trim());
                String category = categoryBox.getValue();
                String imagePath = selectedImageFile != null ? selectedImageFile.getAbsolutePath() : selected.getImagePath();

                if (name.isEmpty() || ingredients.isEmpty() || instructions.isEmpty() || category == null || category.isEmpty()) {
                    System.out.println("Bitte alle Felder ausfüllen.");
                    return;
                }

                DatabaseHelper.updateRecipe(selected.getId(), name, ingredients, instructions, time, difficulty, category, imagePath);
                clearFields();
                refreshTable();
            } catch (NumberFormatException e) {
                System.out.println("Ungültige Eingabe bei Zeit oder Schwierigkeit.");
            }
        }
    }

    /**
     * Löscht das aktuell ausgewählte Rezept aus der Datenbank.
     */
    private void deleteRecipe() {
        Recipe selected = tableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            DatabaseHelper.deleteRecipe(selected.getId());
            clearFields();
            refreshTable();
        }
    }

    /**
     * Aktualisiert die Tabelle mit allen Rezepten aus der Datenbank.
     */
    private void refreshTable() {
        ObservableList<Recipe> data = FXCollections.observableArrayList(DatabaseHelper.getAllRecipes());
        tableView.setItems(data);
    }

    /**
     * Leert alle Eingabefelder und setzt die Bildvorschau zurück.
     */
    private void clearFields() {
        nameField.clear();
        ingredientsField.clear();
        instructionsArea.clear();
        timeField.clear();
        difficultyField.clear();
        categoryBox.setValue(null);
        recipeImageView.setImage(null);
        selectedImageFile = null;
    }

}
