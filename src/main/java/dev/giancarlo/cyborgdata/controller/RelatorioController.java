package dev.giancarlo.cyborgdata.controller;


import dev.giancarlo.cyborgdata.model.dto.RelatorioDTO;
import dev.giancarlo.cyborgdata.service.RelatorioService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class RelatorioController {

    private final RelatorioService relatorioService;

    public RelatorioController(RelatorioService relatorioService) {
        this.relatorioService = relatorioService;
    }

    @GetMapping("/rel")
    public RelatorioDTO gerarRelatorio(@RequestParam String country) {
        return relatorioService.gerarRelatorio(country);
    }

}
