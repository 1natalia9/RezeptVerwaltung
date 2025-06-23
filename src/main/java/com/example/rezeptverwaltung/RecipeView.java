package com.example.rezeptverwaltung;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

/**
 * Diese Klasse stellt die Benutzeroberfläche für die Anzeige und Verwaltung von Rezepten dar.
 */
public class RecipeView extends GridPane {

    private final TextField nameField;
    private final TextField ingredientsField;
    private final TextArea instructionsArea;
    private final TextField timeField;
    private final TextField difficultyField;

    private final TableView<Recipe> tableView;

    /**
     * Konstruktor: Initialisiert die Benutzeroberfläche und deren Komponenten.
     */
    public RecipeView() {
        setPadding(new Insets(10));
        setVgap(10);
        setHgap(10);

        nameField = new TextField();
        ingredientsField = new TextField();
        instructionsArea = new TextArea();
        timeField = new TextField();
        difficultyField = new TextField();

        instructionsArea.setPrefRowCount(4);

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

        Button speichernBtn = new Button("Speichern");
        Button anzeigenBtn = new Button("Liste aktualisieren");
        Button bearbeitenBtn = new Button("Bearbeiten");
        Button loeschenBtn = new Button("Löschen");

        add(speichernBtn, 0, 5);
        add(anzeigenBtn, 1, 5);
        add(bearbeitenBtn, 0, 6);
        add(loeschenBtn, 1, 6);

        tableView = new TableView<>();

        TableColumn<Recipe, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Recipe, String> ingredientsCol = new TableColumn<>("Zutaten");
        ingredientsCol.setCellValueFactory(new PropertyValueFactory<>("ingredients"));

        TableColumn<Recipe, Integer> timeCol = new TableColumn<>("Zeit (min)");
        timeCol.setCellValueFactory(new PropertyValueFactory<>("time"));

        TableColumn<Recipe, Integer> difficultyCol = new TableColumn<>("Schwierigkeit");
        difficultyCol.setCellValueFactory(new PropertyValueFactory<>("difficulty"));

        tableView.getColumns().addAll(nameCol, ingredientsCol, timeCol, difficultyCol);
        tableView.setPrefHeight(200);

        add(tableView, 0, 7, 2, 1);

        speichernBtn.setOnAction(e -> saveRecipe());
        anzeigenBtn.setOnAction(e -> refreshTable());
        bearbeitenBtn.setOnAction(e -> editRecipe());
        loeschenBtn.setOnAction(e -> deleteRecipe());

        tableView.setOnMouseClicked(event -> {
            Recipe selected = tableView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                nameField.setText(selected.getName());
                ingredientsField.setText(selected.getIngredients());
                instructionsArea.setText(selected.getInstructions());
                timeField.setText(String.valueOf(selected.getTime()));
                difficultyField.setText(String.valueOf(selected.getDifficulty()));
            }
        });

        refreshTable();
    }

    /**
     * Speichert ein neues Rezept in der Datenbank.
     */
    private void saveRecipe() {
        try {
            String name = nameField.getText().trim();
            String ingredients = ingredientsField.getText().trim();
            String instructions = instructionsArea.getText().trim();
            int time = Integer.parseInt(timeField.getText().trim());
            int difficulty = Integer.parseInt(difficultyField.getText().trim());

            if (name.isEmpty() || ingredients.isEmpty() || instructions.isEmpty()) {
                System.out.println("Bitte alle Felder ausfüllen.");
                return;
            }

            DatabaseHelper.insertRecipe(name, ingredients, instructions, time, difficulty);

            clearFields();
            refreshTable();

        } catch (NumberFormatException e) {
            System.out.println("Bitte gültige Zahlen für Zeit und Schwierigkeit eingeben.");
        }
    }

    /**
     * Bearbeitet das aktuell ausgewählte Rezept.
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

                DatabaseHelper.updateRecipe(selected.getId(), name, ingredients, instructions, time, difficulty);

                clearFields();
                refreshTable();
            } catch (NumberFormatException e) {
                System.out.println("Ungültige Eingabe bei Zeit oder Schwierigkeit.");
            }
        }
    }

    /**
     * Löscht das aktuell ausgewählte Rezept.
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
     * Aktualisiert die Tabelle mit den neuesten Rezeptdaten.
     */
    private void refreshTable() {
        ObservableList<Recipe> data = FXCollections.observableArrayList(DatabaseHelper.getAllRecipes());
        tableView.setItems(data);
    }

    /**
     * Leert alle Eingabefelder.
     */
    private void clearFields() {
        nameField.clear();
        ingredientsField.clear();
        instructionsArea.clear();
        timeField.clear();
        difficultyField.clear();
    }
}
