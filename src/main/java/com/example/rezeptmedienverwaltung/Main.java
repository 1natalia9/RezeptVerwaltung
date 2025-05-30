package com.example.rezeptmedienverwaltung;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        // Tabelle erstellen, falls sie noch nicht existiert
        DatabaseHelper.createTablesIfNotExists();

        stage.setTitle("Rezept- & Medienverwaltung");

        TabPane tabPane = new TabPane();

        Tab recipeTab = new Tab("Rezepte", new RecipeView());
        Tab mediaTab = new Tab("Medien", new MediaView());

        tabPane.getTabs().addAll(recipeTab, mediaTab);

        Scene scene = new Scene(tabPane, 600, 400);
        stage.setScene(scene);
        stage.show();
    }

}

