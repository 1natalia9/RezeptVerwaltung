package com.example.rezeptverwaltung;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Die Klasse {@code RecipeExporter} bietet eine Methode zum Exportieren
 * von Rezeptdaten aus der Datenbank in eine CSV-Datei.
 */
public class RecipeExporter {

    /**
     * Exportiert alle in der Datenbank gespeicherten Rezepte in eine CSV-Datei.
     * Die Datei enthält die Spalten: ID, Titel, Zutaten, Zubereitung, Zeit, Schwierigkeit.
     *
     * @param filename Der Pfad und Name der zu erstellenden CSV-Datei
     */
    public static void exportRecipesToCSV(String filename) {
        try (FileWriter writer = new FileWriter(filename)) {
            // CSV-Header schreiben
            writer.write("ID;Titel;Zutaten;Zubereitung;Zeit;Schwierigkeit\n");

            // Rezepte aus der Datenbank abrufen
            List<Recipe> recipes = DatabaseHelper.getAllRecipes();

            // Jedes Rezept als CSV-Zeile schreiben
            for (Recipe recipe : recipes) {
                writer.write(String.format("%d;\"%s\";\"%s\";\"%s\";%d;%d\n",
                        recipe.getId(),
                        escape(recipe.getName()),
                        escape(recipe.getIngredients()),
                        escape(recipe.getInstructions()),
                        recipe.getTime(),
                        recipe.getDifficulty()));
            }
        } catch (IOException e) {
            // Fehler beim Schreiben der Datei behandeln
            e.printStackTrace();
        }
    }

    /**
     * Hilfsmethode zum Escapen von Anführungszeichen in Textfeldern,
     * um CSV-Formatierung zu gewährleisten.
     *
     * @param text Der zu bearbeitende Text
     * @return Der Text mit doppelten Anführungszeichen ersetzt
     */
    private static String escape(String text) {
        return text.replace("\"", "\"\"");
    }
}
