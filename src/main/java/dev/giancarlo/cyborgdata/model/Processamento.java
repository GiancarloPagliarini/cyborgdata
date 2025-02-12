package dev.giancarlo.cyborgdata.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Processamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String hash;
    private String company;
    private String status;
    private int percentProcessado;
    private long tempoExecucao; // minutos

    @Lob
    private String relatorioJson;
}
