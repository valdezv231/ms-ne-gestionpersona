package com.example.msnegestionpersonas.controller;

import com.example.msnegestionpersonas.entity.Persona;
import com.example.msnegestionpersonas.service.PersonaService;
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
    public Mono<Persona> crear(@RequestBody Persona persona) {
        return service.crear(persona);
    }

    @PutMapping("/{id}")
    public Mono<Persona> actualizar(@PathVariable UUID id, @RequestBody Persona persona) {
        return service.actualizar(id, persona);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> eliminar(@PathVariable UUID id) {
        return service.eliminar(id);
    }
}

