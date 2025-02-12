package dev.giancarlo.cyborgdata.controller;

import dev.giancarlo.cyborgdata.model.PendenteResponse;
import dev.giancarlo.cyborgdata.service.PendenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class PendenteController {
    @Autowired
    private PendenteService pendenteService;

    @GetMapping("/pendente")
    public PendenteResponse getPendente() {
        return pendenteService.getPendenteData();
    }
}
