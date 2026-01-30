package com.example.msnegestionpersonas.service;

import com.example.msnegestionpersonas.dto.PersonaRequest;
import com.example.msnegestionpersonas.entity.Persona;
import com.example.msnegestionpersonas.repository.PersonaRepository;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PersonaService {
    private final PersonaRepository repository;

    public Flux<Persona> listar(Integer edad, String tipoDocumento) {

        return repository.buscar(edad, tipoDocumento)
                .doOnSubscribe(s ->
                        log.info("Listar personas | edad={} | tipoDocumento={}", edad, tipoDocumento))
                .doOnComplete(() ->
                        log.info("Listado de personas finalizado"))
                .doOnError(e ->
                        log.error("Error al listar personas", e));
    }

    public Mono<Persona> buscarPorId(UUID id) {

        return repository.findById(id)
                .doOnSubscribe(s ->
                        log.info("Buscar persona por id={}", id))
                .doOnSuccess(p ->
                        log.info("Persona encontrada id={}", id))
                .doOnError(e ->
                        log.error("Error al buscar persona id={}", id, e));
    }

    public Mono<Persona> crear(PersonaRequest request) {

        return Mono.fromSupplier(() -> {
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
                    return persona;
                })
                .doOnSubscribe(s ->
                        log.info("Creando persona | documento={}", request.getNumeroDocumento()))
                .flatMap(repository::save)
                .doOnSuccess(p ->
                        log.info("Persona creada con id={}", p.getId()))
                .doOnError(e ->
                        log.error("Error al crear persona", e));
    }

    public Mono<Persona> actualizar(UUID id, PersonaRequest request) {

        return repository.findById(id)
                .doOnSubscribe(s ->
                        log.info("Actualizar persona id={}", id))
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
                })
                .doOnSuccess(p ->
                        log.info("Persona actualizada id={}", id))
                .doOnError(e ->
                        log.error("Error al actualizar persona id={}", id, e));
    }

    public Mono<Void> eliminar(UUID id) {

        return repository.findById(id)
                .doOnSubscribe(s ->
                        log.info("Eliminando persona id={}", id))
                .flatMap(p -> {
                    p.setEliminado(true);
                    p.setActualizadoFecha(LocalDateTime.now());
                    return repository.save(p);
                })
                .doOnSuccess(p ->
                        log.info("Persona eliminada id={}", id))
                .doOnError(e ->
                        log.error("Error al eliminar persona id={}", id, e))
                .then();
    }
}
