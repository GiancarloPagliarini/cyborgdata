package dev.giancarlo.cyborgdata.controller;

import dev.giancarlo.cyborgdata.service.RelatorioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class RelatorioController {

    private final RelatorioService relatorioService;

    public RelatorioController(RelatorioService relatorioService) {
        this.relatorioService = relatorioService;
    }

    @GetMapping("/rel")
    public ResponseEntity<Map<String, Object>> obterRelatorio() {
        return ResponseEntity.ok(relatorioService.gerarRelatorio());
    }
}
