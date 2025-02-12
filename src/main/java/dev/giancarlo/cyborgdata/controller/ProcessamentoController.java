package dev.giancarlo.cyborgdata.controller;

import dev.giancarlo.cyborgdata.model.ProcessamentoStatus;
import dev.giancarlo.cyborgdata.model.dto.ProcessamentoStatusDTO;
import dev.giancarlo.cyborgdata.service.ProcessamentoService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api")
public class ProcessamentoController {
    private final ProcessamentoService processamentoService;

    public ProcessamentoController(ProcessamentoService processamentoService) {
        this.processamentoService = processamentoService;
    }

    @GetMapping("/lot/{hash}")
    public ProcessamentoStatusDTO getProcessamento(@PathVariable String hash) {
        ProcessamentoStatus status = processamentoService.getProcessamentoStatus((hash));

        if (status == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Processamento não encontrado para o hash: " + hash);
        }

        return new ProcessamentoStatusDTO(
                status.getCompany(),
                status.getRuntime(),
                status.getPercentual(),
                status.getStatus()
        );
    }
}
