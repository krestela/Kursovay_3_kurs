package com.example.kursovay_3_kurs.model;

public enum Colour {
    BLUE("Синий"), RED("Красный"), BLACK("Черный");
    private final String colour;

    Colour(String colour) {
        this.colour = colour;
    }

    public String getColour() {
        return colour;
    }
}
