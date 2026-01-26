package com.example.msnegestionpersonas.service;

import com.example.msnegestionpersonas.dto.PersonaRequest;
import com.example.msnegestionpersonas.entity.Persona;
import com.example.msnegestionpersonas.repository.PersonaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PersonaService {
    private final PersonaRepository repository;

    public Flux<Persona> listar(Integer edad, String tipoDocumento) {
        return repository.buscar(edad, tipoDocumento);
    }

    public Mono<Persona> crear(PersonaRequest dto) {
        Persona persona = new Persona();
        persona.setNombres(dto.getNombres());
        persona.setApellidoPaterno(dto.getApellidoPaterno());
        persona.setApellidoMaterno(dto.getApellidoMaterno());
        persona.setNumeroDocumento(dto.getNumeroDocumento());
        persona.setTipoDocumento(dto.getTipoDocumento());
        persona.setEdad(dto.getEdad().toString());
        persona.setTipoCliente(dto.getTipoCliente());

        persona.setCreadoPor("Admin");
        persona.setCreadoFecha(LocalDateTime.now());
        persona.setEliminado(false);

        return repository.save(persona);
    }

    public Mono<Persona> actualizar(UUID id, PersonaRequest dto) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Persona no encontrada")))
                .flatMap(p -> {
                    p.setNombres(dto.getNombres());
                    p.setApellidoPaterno(dto.getApellidoPaterno());
                    p.setApellidoMaterno(dto.getApellidoMaterno());
                    p.setTipoDocumento(dto.getTipoDocumento());
                    p.setEdad(dto.getEdad().toString());
                    p.setTipoCliente(dto.getTipoCliente());

                    p.setActualizadoPor("Admin");
                    p.setActualizadoFecha(LocalDateTime.now());
                    return repository.save(p);
                });
    }

    public Mono<Void> eliminar(UUID id) {
        return repository.findById(id).flatMap(p -> {
            p.setEliminado(true);
            p.setActualizadoFecha(LocalDateTime.now());
            return repository.save(p);
        }).then();
    }
}
