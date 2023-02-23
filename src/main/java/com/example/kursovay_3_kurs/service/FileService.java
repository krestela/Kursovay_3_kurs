package com.example.kursovay_3_kurs.service;

import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Path;

@Service
public interface FileService {
    boolean saveToFile(String json);

    String readToFile();

    boolean cleanDataFile();

    File getDataFileSocks();

    Path createTempFile(String suffix);
}
