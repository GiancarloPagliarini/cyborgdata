package dev.giancarlo.cyborgdata.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProcessamentoStatusDTO {
    private String company;
    private String runtime;
    private String processing;
    private String status;
}
