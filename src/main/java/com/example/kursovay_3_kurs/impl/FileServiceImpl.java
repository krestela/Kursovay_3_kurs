package com.example.kursovay_3_kurs.impl;

import com.example.kursovay_3_kurs.service.FileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class FileServiceImpl implements FileService {
    @Value("${path.to.data.file}")
    private String dataSocksFile;
    @Value("${name.of.data.file}")
    private String dataSocksName;

    @Override
    public boolean saveToFile(String json) {
        try {
            Files.writeString(Path.of(dataSocksFile, dataSocksName), json);
            return true;
        } catch (IOException e) {
            return false;
        }

    }

    @Override
    public String readToFile() {
        try {
            return Files.readString(Path.of(dataSocksFile, dataSocksName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public boolean cleanDataFile() {
        try {
            Path path = Path.of(dataSocksFile, dataSocksName);
            Files.deleteIfExists(path);
            Files.createFile(path);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public File getDataFileSocks() {
        File recipe = new File(dataSocksFile + "/" + dataSocksName);
        return recipe;
    }

    @Override
    public Path createTempFile(String suffix){
        try {
            return Files.createTempFile(Path.of(dataSocksFile), "tempFile", suffix);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
