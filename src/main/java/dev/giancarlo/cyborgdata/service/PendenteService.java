package dev.giancarlo.cyborgdata.service;

import com.fasterxml.jackson.databind.JsonNode;
import dev.giancarlo.cyborgdata.dto.PendenteResponseDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

@Service
public class PendenteService {

    private final WebClient webClient;

    public PendenteService(WebClient webClient) {
        this.webClient = webClient;
    }

    public List<PendenteResponseDTO> buscarPendentes() {
        JsonNode response = webClient.get()
                .uri("/api/pendente") // Apenas o path, pois a baseUrl já está configurada
                .retrieve()
                .bodyToMono(JsonNode.class)
                .block(); // Bloqueia até obter a resposta (sincrono)

        if (response == null || !response.get("success").asBoolean()) {
            throw new RuntimeException("Erro ao buscar dados externos");
        }

        List<PendenteResponseDTO> lista = new ArrayList<>();
        for (JsonNode node : response.get("data")) {
            lista.add(new PendenteResponseDTO(
                    node.get("hash").asText(),
                    node.get("company").asText()
            ));
        }
        return lista;
    }
}


