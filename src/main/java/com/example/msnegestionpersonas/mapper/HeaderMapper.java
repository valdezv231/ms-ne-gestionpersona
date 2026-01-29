package com.example.msnegestionpersonas.mapper;

import com.example.msnegestionpersonas.dto.Header;
import org.springframework.http.HttpHeaders;

public class HeaderMapper {

    public static Header from(HttpHeaders headers) {
        return new Header(
                headers.getFirst("Id-Transaccion"),
                headers.getFirst("Application-Name"),
                headers.getFirst("Application-Code"),
                headers.getFirst("Consumer-Id")
        );
    }
}
