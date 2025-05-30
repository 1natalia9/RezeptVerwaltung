package com.example.rezeptmedienverwaltung;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;


public class RecipeView extends GridPane {

    private TextField nameField;
    private TextField ingredientsField;
    private TextArea instructionsArea;
    private TextField timeField;
    private TextField difficultyField;

    public RecipeView() {
        setPadding(new Insets(10));
        setVgap(10);
        setHgap(10);

        // Eingabefelder initialisieren
        nameField = new TextField();
        ingredientsField = new TextField();
        instructionsArea = new TextArea();
        timeField = new TextField();
        difficultyField = new TextField();

        // Layout
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

        add(speichernBtn, 0, 5);
        add(anzeigenBtn, 1, 5);

        // TableView erstellen
        TableView<Recipe> tableView = new TableView<>();

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

        add(tableView, 0, 6, 2, 1);

        // Button-Handler
        speichernBtn.setOnAction(e -> saveRecipe());

        anzeigenBtn.setOnAction(e -> {
            ObservableList<Recipe> data = FXCollections.observableArrayList(DatabaseHelper.getAllRecipes());
            tableView.setItems(data);
        });
    }

    private void saveRecipe() {
        try {
            String name = nameField.getText();
            String ingredients = ingredientsField.getText();
            String instructions = instructionsArea.getText();
            int time = Integer.parseInt(timeField.getText());
            int difficulty = Integer.parseInt(difficultyField.getText());

            DatabaseHelper.insertRecipe(name, ingredients, instructions, time, difficulty);
        } catch (NumberFormatException e) {
            System.out.println("Bitte gültige Zahlen für Zeit und Schwierigkeit eingeben.");
        }
    }



}

