package com.example.rezeptverwaltung;

/**
 * Repräsentiert ein Rezept mit allen relevanten Informationen.
 */
public class Recipe {
    private final int id;
    private final String name;
    private final String ingredients;
    private final String instructions;
    private final int time;
    private final int difficulty;

    /**
     * Konstruktor zum Erstellen eines neuen Rezeptobjekts.
     *
     * @param id           die eindeutige ID des Rezepts
     * @param name         der Name des Rezepts
     * @param ingredients  die Zutaten des Rezepts
     * @param instructions die Zubereitungsanleitung
     * @param time         die Zubereitungszeit in Minuten
     * @param difficulty   der Schwierigkeitsgrad (1–5)
     */
    public Recipe(int id, String name, String ingredients, String instructions, int time, int difficulty) {
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.time = time;
        this.difficulty = difficulty;
    }

    // Getter-Methoden

    /**
     * Gibt die ID des Rezepts zurück.
     *
     * @return die ID
     */
    public int getId() {
        return id;
    }

    /**
     * Gibt den Namen des Rezepts zurück.
     *
     * @return der Name
     */
    public String getName() {
        return name;
    }

    /**
     * Gibt die Zutaten des Rezepts zurück.
     *
     * @return die Zutaten
     */
    public String getIngredients() {
        return ingredients;
    }

    /**
     * Gibt die Zubereitungsanleitung zurück.
     *
     * @return die Anleitung
     */
    public String getInstructions() {
        return instructions;
    }

    /**
     * Gibt die Zubereitungszeit in Minuten zurück.
     *
     * @return die Zeit
     */
    public int getTime() {
        return time;
    }

    /**
     * Gibt den Schwierigkeitsgrad zurück.
     *
     * @return der Schwierigkeitsgrad (1–5)
     */
    public int getDifficulty() {
        return difficulty;
    }
}
