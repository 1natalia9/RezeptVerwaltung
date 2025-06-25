package com.example.rezeptverwaltung;

/**
 * Repräsentiert ein Rezept mit allen relevanten Informationen wie Name, Zutaten,
 * Zubereitung, Zeit, Schwierigkeitsgrad, Kategorie und Bildpfad.
 */
public class Recipe {
    private int id;
    private String name;
    private String ingredients;
    private String instructions;
    private int time;
    private int difficulty;
    private String category;
    private String imagePath;

    /**
     * Konstruktor zum Erstellen eines neuen Rezeptobjekts.
     *
     * @param id           die eindeutige ID des Rezepts
     * @param name         der Name des Rezepts
     * @param ingredients  die Zutaten des Rezepts
     * @param instructions die Zubereitungsanleitung
     * @param time         die Zubereitungszeit in Minuten
     * @param difficulty   der Schwierigkeitsgrad (1–5)
     * @param category     die Kategorie des Rezepts (z. B. Dessert, Hauptgericht)
     * @param imagePath    der Pfad zum Bild des Rezepts
     */
    public Recipe(int id, String name, String ingredients, String instructions, int time, int difficulty, String category, String imagePath) {
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.time = time;
        this.difficulty = difficulty;
        this.category = category;
        this.imagePath = imagePath;
    }

    /** @return die ID des Rezepts */
    public int getId() {
        return id;
    }

    /** @return der Name des Rezepts */
    public String getName() {
        return name;
    }

    /** @return die Zutaten des Rezepts */
    public String getIngredients() {
        return ingredients;
    }

    /** @return die Zubereitungsanleitung */
    public String getInstructions() {
        return instructions;
    }

    /** @return die Zubereitungszeit in Minuten */
    public int getTime() {
        return time;
    }

    /** @return der Schwierigkeitsgrad (1–5) */
    public int getDifficulty() {
        return difficulty;
    }

    /** @return die Kategorie des Rezepts */
    public String getCategory() {
        return category;
    }

    /** @return der Pfad zum Bild des Rezepts */
    public String getImagePath() {
        return imagePath;
    }

    /** @param name der neue Name des Rezepts */
    public void setName(String name) {
        this.name = name;
    }

    /** @param ingredients die neuen Zutaten */
    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    /** @param instructions die neue Zubereitungsanleitung */
    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    /** @param time die neue Zubereitungszeit in Minuten */
    public void setTime(int time) {
        this.time = time;
    }

    /** @param difficulty der neue Schwierigkeitsgrad (1–5) */
    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    /** @param category die neue Kategorie des Rezepts */
    public void setCategory(String category) {
        this.category = category;
    }

    /** @param imagePath der neue Pfad zum Bild */
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
