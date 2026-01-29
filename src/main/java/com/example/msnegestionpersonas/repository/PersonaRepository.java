package com.example.msnegestionpersonas.repository;

import com.example.msnegestionpersonas.entity.Persona;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

import java.util.UUID;

public interface PersonaRepository extends ReactiveCrudRepository<Persona, UUID> {

    @Query("""
        SELECT * FROM personas 
        WHERE eliminado = 0
        AND (:edad IS NULL OR edad = :edad)
        AND (:tipoDocumento IS NULL OR tipo_documento = :tipoDocumento)
    """)
    Flux<Persona> buscar(Integer edad, String tipoDocumento);
}
