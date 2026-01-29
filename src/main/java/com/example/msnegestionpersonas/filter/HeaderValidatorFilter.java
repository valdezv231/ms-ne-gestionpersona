package com.example.msnegestionpersonas.filter;

import com.example.msnegestionpersonas.dto.Header;
import com.example.msnegestionpersonas.mapper.HeaderMapper;
import com.example.msnegestionpersonas.service.HeaderValidationService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@Component
public class HeaderValidatorFilter implements WebFilter {

    private final HeaderValidationService validationService;

    public HeaderValidatorFilter(HeaderValidationService validationService) {
        this.validationService = validationService;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

        Header dto = HeaderMapper.from(exchange.getRequest().getHeaders());

        return validationService.validar(dto)
                .flatMap(error -> buildError(exchange, error))
                .switchIfEmpty(chain.filter(exchange));
    }

    private Mono<Void> buildError(ServerWebExchange exchange, String message) {

        exchange.getResponse().setStatusCode(HttpStatus.BAD_REQUEST);
        exchange.getResponse().getHeaders().add("Content-Type", "application/json");

        String json = """
                {
                  "error": "Falla de Seguridad",
                  "detalle": "%s"
                }
                """.formatted(message);

        var buffer = exchange.getResponse()
                .bufferFactory()
                .wrap(json.getBytes(StandardCharsets.UTF_8));

        return exchange.getResponse().writeWith(Mono.just(buffer));
    }
}
