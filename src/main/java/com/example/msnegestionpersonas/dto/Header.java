package com.example.msnegestionpersonas.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Header {

    private String idTransaccion;
    private String applicationName;
    private String applicationCode;
    private String consumerId;
}