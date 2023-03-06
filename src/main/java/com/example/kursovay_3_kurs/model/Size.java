package com.example.kursovay_3_kurs.model;

public enum Size {
    XS(35), S(36), M(37), L(38);

    private final int size;

    Size(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }
}
