package com.example.rezeptverwaltung;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

/**
 * Die Klasse {@code LoginView} stellt eine Benutzeroberfläche für die Anmeldung
 * und Registrierung von Benutzern bereit. Sie wird als Teil eines {@link TabPane}
 * verwendet und ermöglicht die Authentifizierung über die Datenbank.
 */
public class LoginView extends VBox {

    /**
     * Konstruktor für die LoginView.
     * Erstellt die Eingabefelder, Buttons und das Layout für Login und Registrierung.
     *
     * @param tabPane Das übergeordnete {@link TabPane}, in das nach erfolgreichem Login
     *                ein neuer Tab für die Rezeptverwaltung eingefügt wird.
     */
    public LoginView(TabPane tabPane) {
        setPadding(new Insets(20));
        setSpacing(10);

        // Eingabefeld für den Benutzernamen
        TextField usernameField = new TextField();
        usernameField.setPromptText("Benutzername");

        // Eingabefeld für das Passwort
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Passwort");

        // Button zum Einloggen
        Button loginButton = new Button("Anmelden");

        // Button zur Registrierung
        Button registerButton = new Button("Registrieren");

        // Label zur Anzeige von Statusmeldungen
        Label messageLabel = new Label();

        // Login-Logik
        loginButton.setOnAction(e -> {
            boolean success = DatabaseHelper.loginUser(usernameField.getText(), passwordField.getText());
            messageLabel.setText(success ? "Login erfolgreich!" : "Login fehlgeschlagen.");
            if (success) {
                // Nach erfolgreichem Login: neuen Tab öffnen und Login-Tab entfernen
                Tab recipeTab = new Tab("Benutzer-Rezepte", new RecipeView());
                recipeTab.setClosable(false);
                tabPane.getTabs().add(recipeTab);
                tabPane.getSelectionModel().select(recipeTab);
                tabPane.getTabs().remove(0); // Login-Tab entfernen
            }
        });

        // Registrierungs-Logik
        registerButton.setOnAction(e -> {
            boolean success = DatabaseHelper.registerUser(usernameField.getText(), passwordField.getText());
            messageLabel.setText(success ? "Registrierung erfolgreich!" : "Benutzername bereits vergeben.");
        });

        // Komponenten zur Ansicht hinzufügen
        getChildren().addAll(usernameField, passwordField, loginButton, registerButton, messageLabel);
    }
}
