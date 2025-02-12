package dev.giancarlo.cyborgdata.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PendenteResponseDTO {
    private String hash;
    private String company;
}

