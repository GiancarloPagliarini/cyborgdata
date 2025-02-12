package dev.giancarlo.cyborgdata.model.dto;

import java.util.Map;

public record ResponsePendenteDTO(
        String hash,
        String company,
        String pais,
        String sigla,
        String email,
        String cnpj,
        Map<String, String> documentos) {}