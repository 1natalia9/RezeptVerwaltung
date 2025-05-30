package com.example.rezeptmedienverwaltung;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

public class MediaView extends GridPane {
    public MediaView() {
        setPadding(new Insets(10));
        setVgap(10);
        setHgap(10);

        add(new Label("Titel:"), 0, 0);
        add(new TextField(), 1, 0);

        add(new Label("Typ (Buch/Film):"), 0, 1);
        add(new TextField(), 1, 1);

        add(new Label("Autor/Regisseur:"), 0, 2);
        add(new TextField(), 1, 2);

        add(new Label("Genre:"), 0, 3);
        add(new TextField(), 1, 3);

        add(new Label("Bewertung (1–5):"), 0, 4);
        add(new TextField(), 1, 4);

        Button speichernBtn = new Button("Speichern");
        Button anzeigenBtn = new Button("Liste aktualisieren");

        add(speichernBtn, 0, 5);
        add(anzeigenBtn, 1, 5);
    }
}

