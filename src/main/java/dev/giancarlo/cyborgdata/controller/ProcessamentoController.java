package dev.giancarlo.cyborgdata.controller;

import dev.giancarlo.cyborgdata.dto.ProcessamentoStatusDTO;
import dev.giancarlo.cyborgdata.service.ProcessamentoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ProcessamentoController {
    private final ProcessamentoService processamentoService;

    public ProcessamentoController(ProcessamentoService processamentoService) {
        this.processamentoService = processamentoService;
    }

    @GetMapping("/lot/{hash}")
    public ResponseEntity<ProcessamentoStatusDTO> consultarStatus(@PathVariable String hash) {
        return ResponseEntity.ok(processamentoService.consultarProcessamento(hash));
    }
}
