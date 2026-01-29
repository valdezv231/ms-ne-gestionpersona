package com.example.msnegestionpersonas.service;

import com.example.msnegestionpersonas.dto.Header;
import com.example.msnegestionpersonas.repository.ApplicationRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class HeaderValidationService {

    private final ApplicationRepository repository;

    public HeaderValidationService(ApplicationRepository repository) {
        this.repository = repository;
    }

    public Mono<String> validar(Header dto) {

        if (dto.getIdTransaccion() == null || dto.getIdTransaccion().isBlank()) {
            return Mono.just("Falta el header: Id-Transaccion");
        }
        if (dto.getApplicationName() == null || dto.getApplicationName().isBlank()) {
            return Mono.just("Falta el header: Application-Name");
        }
        if (dto.getApplicationCode() == null || dto.getApplicationCode().isBlank()) {
            return Mono.just("Falta el header: Application-Code");
        }
        if (dto.getConsumerId() == null || dto.getConsumerId().isBlank()) {
            return Mono.just("Falta el header: Consumer-Id");
        }

        return repository.existsByApplicationCodeAndApplicationNameAndConsumerId(
                dto.getApplicationCode().trim(),
                dto.getApplicationName().trim(),
                dto.getConsumerId().trim()
        ).flatMap(exists -> {
            if (!exists) {
                return Mono.just("Application-Name, Application-Code o Consumer-Id no válidos");
            }
            return Mono.empty();
        });
    }
}
