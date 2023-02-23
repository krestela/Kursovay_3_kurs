package com.example.kursovay_3_kurs.impl;

import com.example.kursovay_3_kurs.exception.BadRequest;
import com.example.kursovay_3_kurs.model.Colour;
import com.example.kursovay_3_kurs.model.Size;
import com.example.kursovay_3_kurs.model.Socks;
import com.example.kursovay_3_kurs.service.FileService;
import com.example.kursovay_3_kurs.service.SocksService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;


import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

@Service
public class SocksServiceImpl implements SocksService {
    final private FileService fileService;
    private static Map<Long, Socks> socksMap = new TreeMap<>();
    @Min(0)
    @Max(100)
    private static int signature;
    private static long id = 1;

    public SocksServiceImpl(FileService fileService) {
        this.fileService = fileService;
    }

    @Override
    public long addParameters(Socks socks) throws BadRequest {
        if (socksMap.isEmpty()) {
            socksMap.put(id, socks);
            saveToFile();
            return id++;
        }
        Socks newSocks = new Socks(socks.getColour(), socks.getSize(), socks.getStructure(), socks.getQuantity());
        socksMap.put(id, newSocks);
        return id++;


    }

    @Override
    public void takeSocksFrom(Socks socks) throws BadRequest {
        validate(socks);
        if (socksMap.containsValue(socks)) {
            for (Map.Entry<Long, Socks> entry : socksMap.entrySet()) {
                if (entry.getValue().equals(socks)) {
                    long getId = entry.getKey();
                    int getOldQuantity = entry.getValue().getQuantity();
                    int getNewQuantity = socks.getQuantity();
                    if (getOldQuantity >= getNewQuantity) {
                        int quantity = getOldQuantity - getNewQuantity;
                        Socks socksNew = new Socks(socks.getColour(), socks.getSize(), socks.getStructure(),
                                quantity);
                        socksMap.put(getId, socksNew);
                    } else {
                        throw new BadRequest(
                                "На складе не хватает пар носков, в запросе больше на: " + (Math.abs(
                                        getOldQuantity - getNewQuantity)));
                    }
                }
            }
        }
    }

    @Override
    public void saveToFile() {
        try {
            String json = new ObjectMapper().writeValueAsString(socksMap);
            fileService.saveToFile(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteSocks(Colour color, Size size, int cottonPercent, int quantity)
            throws BadRequest {
        Socks socks = new Socks(color, size, cottonPercent, quantity);
        if (socksMap.containsValue(socks)) {
            for (Map.Entry<Long, Socks> entry : socksMap.entrySet()) {
                if (entry.getValue().equals(socks)) {
                    long getId = entry.getKey();
                    int getOldQuantity = entry.getValue().getQuantity();
                    int defectiveQuantitySocks = socks.getQuantity();
                    if (getOldQuantity >= defectiveQuantitySocks) {
                        int quantityNew = getOldQuantity - defectiveQuantitySocks;
                        Socks socksNew = new Socks(socks.getColour(), socks.getSize(), socks.getStructure(),
                                quantityNew);
                        socksMap.put(getId, socksNew);
                    } else {
                        throw new BadRequest("Нечего списать");
                    }
                }
            }
        }
    }

    @Override
    public void readFromFile() {

        try {
            String json = fileService.readToFile();
            socksMap = new ObjectMapper().readValue(json, new TypeReference<TreeMap<Long, Socks>>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void validate(Socks socks) throws BadRequest {
        if (socks.getColour() == null || socks.getSize() == null) {
            throw new BadRequest("Все поля должны быть заполнены");
        }
        if (socks.getQuantity() <= 0) {
            throw new BadRequest("параметр quantity должен быть больше 0");
        }
        if (socks.getStructure() < 0 || socks.getStructure() > 100) {
            throw new BadRequest("параметр structure  должен быть между 0 и 100");
        }
    }

    @Override
    public Collection<Socks> getAllSocks() {
        return socksMap.values();
    }
}


