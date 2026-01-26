package com.example.msnegestionpersonas.controller;

import com.example.msnegestionpersonas.dto.PersonaRequest;
import com.example.msnegestionpersonas.entity.Persona;
import com.example.msnegestionpersonas.service.PersonaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/api/personas")
@RequiredArgsConstructor
public class PersonaController {

    private final PersonaService service;

    @GetMapping
    public Flux<Persona> listar(
            @RequestParam(required = false) Integer edad,
            @RequestParam(required = false) String tipoDocumento) {
        return service.listar(edad, tipoDocumento);
    }

    @PostMapping
    public Mono<Persona> crear(@Valid @RequestBody PersonaRequest dto) {
        return service.crear(dto);
    }

    @PutMapping("/{id}")
    public Mono<Persona> actualizar(@PathVariable UUID id, @Valid @RequestBody PersonaRequest dto) {
        return service.actualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> eliminar(@PathVariable UUID id) {
        return service.eliminar(id);
    }
}

