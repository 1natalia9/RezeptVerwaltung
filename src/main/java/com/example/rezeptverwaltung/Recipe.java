package com.example.rezeptverwaltung;

public class Recipe {
    private int id;
    private String name;
    private String ingredients;
    private String instructions;
    private int time;
    private int difficulty;

    public Recipe(int id, String name, String ingredients, String instructions, int time, int difficulty) {
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.time = time;
        this.difficulty = difficulty;
    }

    // Getter
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getIngredients() {
        return ingredients;
    }

    public String getInstructions() {
        return instructions;
    }

    public int getTime() {
        return time;
    }

    public int getDifficulty() {
        return difficulty;
    }
}

