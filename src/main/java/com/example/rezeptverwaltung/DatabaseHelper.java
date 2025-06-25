package com.example.rezeptverwaltung;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Die Klasse {@code DatabaseHelper} stellt Hilfsmethoden für die Verwaltung
 * einer SQLite-Datenbank bereit, die Rezepte und Benutzerinformationen speichert.
 * Sie ermöglicht das Erstellen von Tabellen, sowie CRUD-Operationen für Rezepte und Benutzer.
 */
public class DatabaseHelper {

    private static final String DB_URL = "jdbc:sqlite:rezept.db";

    /**
     * Stellt eine Verbindung zur SQLite-Datenbank her.
     *
     * @return ein {@link Connection}-Objekt zur Datenbank
     * @throws SQLException wenn ein Fehler beim Verbindungsaufbau auftritt
     */
    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    /**
     * Erstellt die Tabelle "recipes", falls sie noch nicht existiert.
     * Die Tabelle enthält Informationen zu Rezepten wie Name, Zutaten, Anleitung usw.
     */
    public static void createTablesIfNotExists() {
        String sql = "CREATE TABLE IF NOT EXISTS recipes (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT NOT NULL," +
                "ingredients TEXT," +
                "instructions TEXT," +
                "time INTEGER," +
                "difficulty INTEGER," +
                "category TEXT," +
                "imagePath TEXT" +
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
     * @return eine Liste aller gespeicherten {@link Recipe}-Objekte
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
                        rs.getInt("difficulty"),
                        rs.getString("category"),
                        rs.getString("imagePath")
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
     * @param name         Name des Rezepts
     * @param ingredients  Zutatenliste
     * @param instructions Zubereitungsanleitung
     * @param time         Zubereitungszeit in Minuten
     * @param difficulty   Schwierigkeitsgrad (1-5)
     * @param category     Kategorie des Rezepts (z. B. Dessert, Hauptgericht)
     * @param imagePath    Pfad zum Bild des Rezepts
     */
    public static void insertRecipe(String name, String ingredients, String instructions, int time, int difficulty, String category, String imagePath) {
        String sql = "INSERT INTO recipes (name, ingredients, instructions, time, difficulty, category, imagePath) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, ingredients);
            pstmt.setString(3, instructions);
            pstmt.setInt(4, time);
            pstmt.setInt(5, difficulty);
            pstmt.setString(6, category);
            pstmt.setString(7, imagePath);
            pstmt.executeUpdate();
            System.out.println("Rezept gespeichert.");
        } catch (SQLException e) {
            System.out.println("Fehler beim Speichern: " + e.getMessage());
        }
    }

    /**
     * Aktualisiert ein bestehendes Rezept in der Datenbank.
     *
     * @param id           ID des Rezepts
     * @param name         Name des Rezepts
     * @param ingredients  Zutatenliste
     * @param instructions Zubereitungsanleitung
     * @param time         Zubereitungszeit in Minuten
     * @param difficulty   Schwierigkeitsgrad (1-5)
     * @param category     Kategorie des Rezepts
     * @param imagePath    Pfad zum Bild des Rezepts
     */
    public static void updateRecipe(int id, String name, String ingredients, String instructions, int time, int difficulty, String category, String imagePath) {
        String sql = "UPDATE recipes SET name = ?, ingredients = ?, instructions = ?, time = ?, difficulty = ?, category = ?, imagePath = ? WHERE id = ?";

        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, ingredients);
            pstmt.setString(3, instructions);
            pstmt.setInt(4, time);
            pstmt.setInt(5, difficulty);
            pstmt.setString(6, category);
            pstmt.setString(7, imagePath);
            pstmt.setInt(8, id);
            pstmt.executeUpdate();
            System.out.println("Rezept aktualisiert.");
        } catch (SQLException e) {
            System.out.println("Fehler beim Aktualisieren: " + e.getMessage());
        }
    }

    /**
     * Löscht ein Rezept aus der Datenbank anhand seiner ID.
     *
     * @param id ID des zu löschenden Rezepts
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

    /**
     * Erstellt die Tabelle "users", falls sie noch nicht existiert.
     * Diese Tabelle speichert Benutzerinformationen für die Anmeldung.
     */
    public static void createUserTableIfNotExists() {
        String sql = "CREATE TABLE IF NOT EXISTS users (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "username TEXT NOT NULL UNIQUE," +
                "password TEXT NOT NULL" +
                ")";

        try (Connection conn = connect(); Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Tabelle 'users' überprüft/erstellt.");
        } catch (SQLException e) {
            System.out.println("Fehler beim Erstellen der Benutzertabelle: " + e.getMessage());
        }
    }

    /**
     * Registriert einen neuen Benutzer in der Datenbank.
     *
     * @param username Benutzername
     * @param password Passwort (Hinweis: sollte in der Praxis gehasht werden!)
     * @return {@code true}, wenn die Registrierung erfolgreich war, sonst {@code false}
     */
    public static boolean registerUser(String username, String password) {
        String sql = "INSERT INTO users (username, password) VALUES (?, ?)";

        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.executeUpdate();
            System.out.println("Benutzer registriert.");
            return true;
        } catch (SQLException e) {
            System.out.println("Fehler bei der Registrierung: " + e.getMessage());
            return false;
        }
    }

    /**
     * Überprüft die Anmeldedaten eines Benutzers.
     *
     * @param username Benutzername
     * @param password Passwort
     * @return {@code true}, wenn die Anmeldedaten korrekt sind, sonst {@code false}
     */
    public static boolean loginUser(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";

        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.out.println("Fehler bei der Anmeldung: " + e.getMessage());
            return false;
        }
    }
}
