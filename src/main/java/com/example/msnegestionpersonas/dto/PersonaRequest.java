package com.example.msnegestionpersonas.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class PersonaRequest {
    @NotBlank(message = "El nombre es obligatorio")
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$", message = "El nombre solo debe contener letras")
    private String nombres;

    @NotBlank(message = "El apellido paterno es obligatorio")
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$", message = "El apellido paterno solo debe contener letras")
    private String apellidoPaterno;

    @NotBlank(message = "El apellido materno es obligatorio")
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$", message = "El apellido materno solo debe contener letras")
    private String apellidoMaterno;

    @NotBlank(message = "El número de documento es obligatorio")
    @Pattern(regexp = "^[0-9]+$", message = "El número de documento debe contener solo números")
    @Size(min = 8, max = 11, message = "El documento debe tener entre 8 (DNI) y 11 (RUC) dígitos")
    private String numeroDocumento;

    @NotBlank(message = "El tipo de documento es obligatorio")
    @Pattern(regexp = "^(DNI|RUC|CARNET_EXTRANJERIA)$",
            message = "El tipo de documento debe ser DNI, RUC o CARNET_EXTRANJERIA")
    private String tipoDocumento;

    @NotNull(message = "La edad es obligatoria")
    @Min(value = 0, message = "La edad no puede ser negativa")
    @Max(value = 120, message = "Ingrese una edad válida")
    private Integer edad;

    @NotBlank(message = "El tipo de cliente es obligatorio")
    private String tipoCliente;
}
