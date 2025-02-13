package dev.giancarlo.cyborgdata.service;

import com.fasterxml.jackson.databind.JsonNode;
//import dev.giancarlo.cyborgdata.model.Funcionario;
import dev.giancarlo.cyborgdata.model.Processamento;
import dev.giancarlo.cyborgdata.repository.ProcessamentoRepository;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProcessamentoService {

    private final ProcessamentoRepository repository;
    private final WebClient webClient;

    public ProcessamentoService(ProcessamentoRepository repository, WebClient webClient) {
        this.repository = repository;
        this.webClient = webClient;
    }

    @Async
    public void iniciarProcessamento(String hash, String company) {
        Processamento processamento = repository.findByHash(hash).orElse(new Processamento());
        processamento.setHash(hash);
        processamento.setCompany(company);
        processamento.setStatus("Pending");
        processamento.setPercentProcessado(0);
        processamento.setTempoExecucao(0);
        repository.save(processamento);

        try {
            long inicio = System.currentTimeMillis(); // Tempo inicial

            // Atualiza status para "Running"
            atualizarProcessamento(processamento, "Running", 10, null);

            // Buscar JSON da API externa
            JsonNode response = webClient.get()
                    .uri("/api/pendente")
                    .retrieve()
                    .bodyToMono(JsonNode.class)
                    .block();

            if (response == null || !response.get("success").asBoolean()) {
                throw new RuntimeException("Erro ao buscar dados externos");
            }

            // Encontrar o hash correto
            JsonNode empresaNode = null;
            for (JsonNode node : response.get("data")) {
                if (node.get("hash").asText().equals(hash)) {
                    empresaNode = node;
                    break;
                }
            }

            if (empresaNode == null) {
                throw new RuntimeException("Hash não encontrado na API externa.");
            }

            String csvUrl = empresaNode.get("documentos").get("funcionario").asText();
            String filePath = "/tmp/" + hash + ".csv"; // Caminho onde o arquivo será salvo

            // Atualiza status e baixa o CSV para o disco
            atualizarProcessamento(processamento, "Running", 50, null);
            baixarCsvParaArquivo(csvUrl, filePath);

            // Atualiza status após baixar o arquivo
            atualizarProcessamento(processamento, "Running", 80, null);

            // Simula o processamento dos dados do arquivo (ex: ler linhas do CSV)
            Thread.sleep(3000);

            // Finaliza processamento e calcula tempo total
            long fim = System.currentTimeMillis();
            long tempoTotal = (fim - inicio) / 1000 / 60; // Tempo em minutos

            processamento.setTempoExecucao(tempoTotal > 0 ? tempoTotal : 1); // Mínimo 1 minuto
            processamento.setStatus("Completed");
            processamento.setPercentProcessado(100);
            repository.save(processamento);

        } catch (Exception e) {
            atualizarProcessamento(processamento, "Error", 0, null);
            e.printStackTrace(); // Adiciona log para capturar erro
        }
    }


    public Optional<Processamento> consultarProcessamento(String hash) {
        return repository.findByHash(hash);
    }

    private void atualizarProcessamento(Processamento processamento, String status, int progresso, String conteudoCsv) {
        processamento.setStatus(status);
        processamento.setPercentProcessado(progresso);
        if (conteudoCsv != null) {
            processamento.setConteudoCsv(conteudoCsv);
        }
        repository.save(processamento);
    }

    public void baixarCsvParaArquivo(String csvUrl, String hash) {
        try {
            // Diretório temporário do sistema
            String tempDir = System.getProperty("java.io.tmpdir");

            // Garantir que a extensão seja sempre .csv e evitar duplicação
            Path filePath = Path.of(tempDir, "tmp", hash.replace(".csv", "") + ".csv");

            System.out.println("Baixando arquivo CSV para: " + filePath.toAbsolutePath());

            // Criar diretório se não existir
            Files.createDirectories(filePath.getParent());

            // Baixar e salvar o arquivo CSV
            DataBufferUtils.write(
                            webClient.get()
                                    .uri(csvUrl)
                                    .retrieve()
                                    .bodyToFlux(DataBuffer.class),
                            filePath);

            System.out.println("Arquivo baixado com sucesso: " + filePath.toAbsolutePath());

        } catch (IOException e) {
            throw new RuntimeException("Erro ao criar diretório ou salvar arquivo: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao baixar arquivo CSV: " + e.getMessage(), e);
        }
    }

    public List<String> getHashesProcessados() {
        return repository.findAll().stream()
                .filter(p -> "Completed".equals(p.getStatus())) // Filtra apenas os finalizados
                .map(Processamento::getHash)
                .collect(Collectors.toList());
    }
}
