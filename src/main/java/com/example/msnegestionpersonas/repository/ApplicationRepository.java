package com.example.msnegestionpersonas.repository;

import com.example.msnegestionpersonas.entity.Application;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

public interface ApplicationRepository extends R2dbcRepository<Application, Long> {

    Mono<Boolean> existsByApplicationCodeAndApplicationNameAndConsumerId(
            String applicationCode,
            String applicationName,
            String consumerId
    );
}
