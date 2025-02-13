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
    private final ProcessamentoService processamentoService;

    public PendenteService(WebClient webClient, ProcessamentoService processamentoService) {
        this.webClient = webClient;
        this.processamentoService = processamentoService;
    }

    public List<PendenteResponseDTO> buscarPendentes() {
        JsonNode response = webClient.get()
                .uri("/api/pendente")
                .retrieve()
                .bodyToMono(JsonNode.class)
                .block();

        if (response == null || !response.get("success").asBoolean()) {
            throw new RuntimeException("Erro ao buscar dados externos");
        }

        List<PendenteResponseDTO> lista = new ArrayList<>();
        for (JsonNode node : response.get("data")) {
            String hash = node.get("hash").asText();
            String company = node.get("company").asText();

            // Adiciona o item na lista
            lista.add(new PendenteResponseDTO(hash, company));

            // Inicia o processamento de forma assíncrona
            processamentoService.iniciarProcessamento(hash, company);
        }

        return lista;
    }
}


