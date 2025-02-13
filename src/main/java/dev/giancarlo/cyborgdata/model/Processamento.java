package dev.giancarlo.cyborgdata.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@RequiredArgsConstructor
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

    @Lob
    private String conteudoCsv;
}
