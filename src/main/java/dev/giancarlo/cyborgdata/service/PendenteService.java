package dev.giancarlo.cyborgdata.service;

import dev.giancarlo.cyborgdata.model.dto.PendenteDTO;
import dev.giancarlo.cyborgdata.model.PendenteResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class PendenteService {

    private final WebClient webClient;

    public PendenteService(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<List<PendenteDTO>> buscarPendentes() {
        return webClient.get()
                .uri("/api/pendente")
                .retrieve()
                .bodyToMono(PendenteResponse.class)
                .map(PendenteResponse::getData); // Retorna a lista de PendenteDTO
    }
}
