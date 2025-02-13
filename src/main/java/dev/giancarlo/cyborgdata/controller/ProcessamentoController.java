package dev.giancarlo.cyborgdata.controller;

import dev.giancarlo.cyborgdata.dto.ProcessamentoStatusDTO;
import dev.giancarlo.cyborgdata.model.Processamento;
import dev.giancarlo.cyborgdata.service.ProcessamentoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ProcessamentoController {

    private final ProcessamentoService processamentoService;

    public ProcessamentoController(ProcessamentoService processamentoService) {
        this.processamentoService = processamentoService;
    }

    @GetMapping("/lot/{hash}")
    public ResponseEntity<ProcessamentoStatusDTO> consultarStatus(@PathVariable String hash) {
        Optional<Processamento> processamentoOpt = processamentoService.consultarProcessamento(hash);

        if (processamentoOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Processamento processamento = processamentoOpt.get();

        ProcessamentoStatusDTO statusDTO = new ProcessamentoStatusDTO(
                processamento.getCompany(),
                processamento.getTempoExecucao() + " minutos",
                processamento.getPercentProcessado() + "%",
                processamento.getStatus()
        );

        return ResponseEntity.ok(statusDTO);
    }
}
