package com.example.kursovay_3_kurs.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedHashMap;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Socks {
    private Colour colour;
    private Size size;
    private int structure;
    private int quantity;

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Socks socks = (Socks) o;
        return structure == socks.structure && colour == socks.colour && size == socks.size;
    }
}