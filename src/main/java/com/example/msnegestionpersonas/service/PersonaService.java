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

    public Mono<Persona> crear(PersonaRequest request) {
        Persona persona = new Persona();
        persona.setNombres(request.getNombres());
        persona.setApellidoPaterno(request.getApellidoPaterno());
        persona.setApellidoMaterno(request.getApellidoMaterno());
        persona.setNumeroDocumento(request.getNumeroDocumento());
        persona.setTipoDocumento(request.getTipoDocumento());
        persona.setEdad(request.getEdad().toString());
        persona.setTipoCliente(request.getTipoCliente());

        persona.setCreadoPor("Admin");
        persona.setCreadoFecha(LocalDateTime.now());
        persona.setEliminado(false);

        return repository.save(persona);
    }

    public Mono<Persona> actualizar(UUID id, PersonaRequest request) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Persona no encontrada")))
                .flatMap(p -> {
                    p.setNombres(request.getNombres());
                    p.setApellidoPaterno(request.getApellidoPaterno());
                    p.setApellidoMaterno(request.getApellidoMaterno());
                    p.setTipoDocumento(request.getTipoDocumento());
                    p.setEdad(request.getEdad().toString());
                    p.setTipoCliente(request.getTipoCliente());

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
