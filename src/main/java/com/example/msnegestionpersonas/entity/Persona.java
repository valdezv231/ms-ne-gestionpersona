package com.example.msnegestionpersonas.entity;

import lombok.Data;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Table("personas")
@Data
public class Persona {
    private UUID id;

    private String nombres;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String numeroDocumento;
    private String tipoDocumento;
    private String edad;
    private String tipoCliente;

    private String creadoPor;
    private LocalDateTime creadoFecha;
    private String actualizadoPor;
    private LocalDateTime actualizadoFecha;
    private Boolean eliminado;
}
