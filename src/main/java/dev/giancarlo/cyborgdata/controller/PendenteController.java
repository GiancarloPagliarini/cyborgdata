package dev.giancarlo.cyborgdata.controller;

import dev.giancarlo.cyborgdata.model.dto.PendenteDTO;
import dev.giancarlo.cyborgdata.service.PendenteService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PendenteController {

    private final PendenteService pendenteService;

    public PendenteController(PendenteService pendenteService) {
        this.pendenteService = pendenteService;
    }

    @GetMapping("/pendente")
    public Mono<List<PendenteDTO>> obterPendentes() {
        return pendenteService.buscarPendentes();
    }
}
