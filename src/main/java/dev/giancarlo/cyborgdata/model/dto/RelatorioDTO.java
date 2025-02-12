package dev.giancarlo.cyborgdata.model.dto;

import java.util.Map;

public record RelatorioDTO(
        String country,
        int countEmployee,
        String sizeFile,
        String runtime,
        Map<String, Integer> countEmployeeCity,
        Map<String, Integer> countEmployeeState
) { }
