package com.example.msnegestionpersonas.service;

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

    public Mono<Persona> crear(Persona persona) {
        persona.setCreadoPor("Admin");
        persona.setCreadoFecha(LocalDateTime.now());
        persona.setEliminado(false);
        return repository.save(persona);
    }

    public Mono<Persona> actualizar(UUID id, Persona persona) {
        return repository.findById(id).flatMap(p->{
            p.setNombres(persona.getNombres());
            p.setApellidoPaterno(persona.getApellidoPaterno());
            p.setApellidoMaterno(persona.getApellidoMaterno());
            p.setTipoDocumento(persona.getTipoDocumento());
            p.setEdad(persona.getEdad());
            p.setTipoCliente(persona.getTipoCliente());
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
