package com.example.rezeptverwaltung;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import java.io.File;

/**
 * Die Klasse {@code ExportView} stellt eine einfache Benutzeroberfläche bereit,
 * mit der alle gespeicherten Rezepte als CSV-Datei exportiert werden können.
 * Sie verwendet einen {@link FileChooser}, um den Speicherort der Datei auszuwählen.
 */
public class ExportView extends VBox {

    /**
     * Konstruktor für die ExportView.
     * Erstellt ein Label und einen Button, um den Exportvorgang zu starten.
     */
    public ExportView() {
        Label info = new Label("Exportiere alle Rezepte als CSV-Datei:");
        Button exportButton = new Button("Exportieren...");

        // Aktion beim Klicken auf den Export-Button
        exportButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("CSV-Datei speichern");
            fileChooser.setInitialFileName("rezepte.csv");
            fileChooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("CSV-Dateien", "*.csv")
            );

            // Öffnet den Speicherdialog, wenn das Fenster verfügbar ist
            if (this.getScene() != null && this.getScene().getWindow() != null) {
                File file = fileChooser.showSaveDialog(this.getScene().getWindow());
                if (file != null) {
                    RecipeExporter.exportRecipesToCSV(file.getAbsolutePath());
                }
            } else {
                System.err.println("Scene oder Window ist noch nicht initialisiert.");
            }
        });

        // Layout-Eigenschaften
        this.setSpacing(10);
        this.getChildren().addAll(info, exportButton);
    }
}
