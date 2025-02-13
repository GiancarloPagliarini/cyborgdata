package dev.giancarlo.cyborgdata.controller;

import dev.giancarlo.cyborgdata.dto.PendenteResponseDTO;
import dev.giancarlo.cyborgdata.service.PendenteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PendenteController {

    private final PendenteService pendenteService;

    public PendenteController(PendenteService pendenteService){
        this.pendenteService = pendenteService;
    }

    @GetMapping("/pendente")
    public ResponseEntity<List<PendenteResponseDTO>> obterPendentes() {
        List<PendenteResponseDTO> pendentes = pendenteService.buscarPendentes();
        return ResponseEntity.ok(pendentes);
    }

}
