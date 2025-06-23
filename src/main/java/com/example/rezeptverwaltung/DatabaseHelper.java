package com.example.rezeptverwaltung;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Stellt Hilfsmethoden für Datenbankoperationen bereit.
 */
public class DatabaseHelper {

    private static final String DB_URL = "jdbc:sqlite:rezept.db";

    /**
     * Stellt eine Verbindung zur SQLite-Datenbank her.
     *
     * @return das Verbindungsobjekt
     * @throws SQLException wenn ein Datenbankzugriffsfehler auftritt
     */
    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    /**
     * Erstellt die Tabelle "recipes", falls sie noch nicht existiert.
     */
    public static void createTablesIfNotExists() {
        String sql = "CREATE TABLE IF NOT EXISTS recipes (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT NOT NULL," +
                "ingredients TEXT," +
                "instructions TEXT," +
                "time INTEGER," +
                "difficulty INTEGER" +
                ")";

        try (Connection conn = connect(); Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Tabelle 'recipes' überprüft/erstellt.");
        } catch (SQLException e) {
            System.out.println("Fehler beim Erstellen der Tabelle: " + e.getMessage());
        }
    }

    /**
     * Ruft alle Rezepte aus der Datenbank ab.
     *
     * @return eine Liste von Rezepten
     */
    public static List<Recipe> getAllRecipes() {
        List<Recipe> recipes = new ArrayList<>();
        String sql = "SELECT * FROM recipes";

        try (Connection conn = connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Recipe recipe = new Recipe(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("ingredients"),
                        rs.getString("instructions"),
                        rs.getInt("time"),
                        rs.getInt("difficulty")
                );
                recipes.add(recipe);
            }
        } catch (SQLException e) {
            System.out.println("Fehler beim Laden der Rezepte: " + e.getMessage());
        }

        return recipes;
    }

    /**
     * Fügt ein neues Rezept in die Datenbank ein.
     *
     * @param name der Name des Rezepts
     * @param ingredients die benötigten Zutaten
     * @param instructions die Zubereitungsanleitung
     * @param time die Zubereitungszeit in Minuten
     * @param difficulty der Schwierigkeitsgrad (1-5)
     */
    public static void insertRecipe(String name, String ingredients, String instructions, int time, int difficulty) {
        String sql = "INSERT INTO recipes (name, ingredients, instructions, time, difficulty) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, ingredients);
            pstmt.setString(3, instructions);
            pstmt.setInt(4, time);
            pstmt.setInt(5, difficulty);
            pstmt.executeUpdate();
            System.out.println("Rezept gespeichert.");
        } catch (SQLException e) {
            System.out.println("Fehler beim Speichern: " + e.getMessage());
        }
    }

    /**
     * Aktualisiert ein bestehendes Rezept in der Datenbank.
     *
     * @param id die eindeutige ID des Rezepts
     * @param name der Name des Rezepts
     * @param ingredients die benötigten Zutaten
     * @param instructions die Zubereitungsanleitung
     * @param time die Zubereitungszeit in Minuten
     * @param difficulty der Schwierigkeitsgrad (1-5)
     */
    public static void updateRecipe(int id, String name, String ingredients, String instructions, int time, int difficulty) {
        String sql = "UPDATE recipes SET name = ?, ingredients = ?, instructions = ?, time = ?, difficulty = ? WHERE id = ?";

        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, ingredients);
            pstmt.setString(3, instructions);
            pstmt.setInt(4, time);
            pstmt.setInt(5, difficulty);
            pstmt.setInt(6, id);
            pstmt.executeUpdate();
            System.out.println("Rezept aktualisiert.");
        } catch (SQLException e) {
            System.out.println("Fehler beim Aktualisieren: " + e.getMessage());
        }
    }

    /**
     * Löscht ein Rezept aus der Datenbank.
     *
     * @param id die eindeutige ID des Rezepts
     */
    public static void deleteRecipe(int id) {
        String sql = "DELETE FROM recipes WHERE id = ?";

        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            System.out.println("Rezept gelöscht.");
        } catch (SQLException e) {
            System.out.println("Fehler beim Löschen: " + e.getMessage());
        }
    }
}
