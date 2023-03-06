package com.example.kursovay_3_kurs.service;

import com.example.kursovay_3_kurs.exception.BadRequest;
import com.example.kursovay_3_kurs.model.Colour;
import com.example.kursovay_3_kurs.model.Size;
import com.example.kursovay_3_kurs.model.Socks;

import java.util.Collection;

public interface SocksService {
    long addParameters(Socks socks) throws BadRequest;

    void takeSocksFrom(Socks socks) throws BadRequest;

    void saveToFile();

    void deleteSocks(Colour color, Size size, int cottonPercent, int quantity)
            throws BadRequest;

    void readFromFile();

    void validate(Socks socks) throws BadRequest;

    Collection<Socks> getAllSocks();
}
