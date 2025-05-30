package com.example.rezeptmedienverwaltung;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper {

    private static final String DB_URL = "jdbc:sqlite:rezeptmedien.db";

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

//    private static final String DB_URL = "jdbc:mysql://localhost:3306/rezeptmedien";
//    private static final String DB_USER = "root";
//    private static final String DB_PASSWORD = "";
//
//    public static Connection connect() throws SQLException {
//        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
//    }


    public static void createTablesIfNotExists() {
        String sql = "CREATE TABLE IF NOT EXISTS recipes (" +
                "id INT NOT NULL AUTO_INCREMENT," +
                "name VARCHAR(255) NOT NULL," +
                "ingredients TEXT," +
                "instructions TEXT," +
                "time INT," +
                "difficulty INT," +
                "PRIMARY KEY (id)" +
                ")";

        try (Connection conn = connect(); Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Tabelle 'recipes' überprüft/erstellt.");
        } catch (SQLException e) {
            System.out.println("Fehler beim Erstellen der Tabelle: " + e.getMessage());
        }
    }


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
}
