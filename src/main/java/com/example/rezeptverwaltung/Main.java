package com.example.rezeptverwaltung;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

/**
 * Die Hauptklasse der Anwendung zur Rezeptverwaltung.
 * Sie initialisiert die Benutzeroberfläche mit verschiedenen Tabs
 * für Login, Rezeptverwaltung und Exportfunktionen.
 */
public class Main extends Application {

    /**
     * Einstiegspunkt der Anwendung.
     *
     * @param args Kommandozeilenargumente (werden von JavaFX verarbeitet)
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Startet die JavaFX-Anwendung und initialisiert die Benutzeroberfläche.
     *
     * @param stage Das Hauptfenster (Primärbühne) der Anwendung
     */
    @Override
    public void start(Stage stage) {
        // Erstellt notwendige Datenbanktabellen, falls sie noch nicht existieren
        DatabaseHelper.createTablesIfNotExists();
        DatabaseHelper.createUserTableIfNotExists();

        // Setzt den Fenstertitel
        stage.setTitle("Rezeptverwaltung");

        // Erstellt ein TabPane zur Navigation zwischen verschiedenen Ansichten
        TabPane tabPane = new TabPane();

        // Login-Tab: Ermöglicht Benutzerauthentifizierung
        Tab loginTab = new Tab("Login", new LoginView(tabPane));
        loginTab.setClosable(false);
        tabPane.getTabs().add(loginTab);

        // Rezepte-Tab: Zeigt die Rezeptverwaltung an
        Tab recipeTab = new Tab("Rezepte", new RecipeView());
        recipeTab.setClosable(false);
        tabPane.getTabs().add(recipeTab);

        // Export-Tab: Bietet Exportfunktionen für Rezepte
        Tab exportTab = new Tab("Export", new ExportView());
        exportTab.setClosable(false);
        tabPane.getTabs().add(exportTab);

        // Erstellt die Szene und zeigt das Hauptfenster an
        Scene scene = new Scene(tabPane, 800, 600);
        stage.setScene(scene);
        stage.show();
    }
}
