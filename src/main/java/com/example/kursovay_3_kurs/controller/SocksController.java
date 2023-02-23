package com.example.kursovay_3_kurs.controller;

import com.example.kursovay_3_kurs.exception.BadRequest;
import com.example.kursovay_3_kurs.model.Colour;
import com.example.kursovay_3_kurs.model.Size;
import com.example.kursovay_3_kurs.model.Socks;
import com.example.kursovay_3_kurs.service.SocksService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@Service
@RequestMapping("/socks")
@Tag(name = "Носки", description = "Методы")
public class SocksController {
    private SocksService socksService;

    @PostMapping("/api/socks")
    @Operation(description = "Добавление носков на склад")
    public ResponseEntity<Long> addSocks(@RequestBody Socks socks) throws BadRequest {
        long id = socksService.addParameters(socks);
        return ResponseEntity.ok().body(id);
    }

    @PutMapping("/api/socks")
    @Operation(description = "Редактирование носков на складе")
    public ResponseEntity<Void> takeSocks(@Valid @RequestBody Socks socks)
            throws BadRequest {
        socksService.takeSocksFrom(socks);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/api/socks")
    @Operation(description = "Удаление")
    public ResponseEntity<Void> deleteSocks(@RequestParam Colour colour, @RequestParam Size size, @RequestParam Integer structure, @RequestParam Integer quantity) throws BadRequest {
        socksService.deleteSocks(colour, size, structure, quantity);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/api/socks")
    @Operation(description = "Получение списка всех носков")
    public Collection<Socks> getAllSocks() {
        return socksService.getAllSocks();
    }
}
