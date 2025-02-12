package dev.giancarlo.cyborgdata.service;

import dev.giancarlo.cyborgdata.model.dto.RelatorioDTO;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class RelatorioService {

    public RelatorioDTO gerarRelatorio(String country) {
        // Simulação de dados
        int totalEmployees = 167;  // Número total de funcionários
        String fileSize = "150.62 mb";  // Tamanho do arquivo
        String runtime = "15 minutos";  // Tempo de execução

        // Agrupamento por cidade e estado (simulação)
        Map<String, Integer> cityData = new HashMap<>();
        cityData.put("Porto Alegre", 55);
        cityData.put("São Paulo", 40);

        Map<String, Integer> stateData = new HashMap<>();
        stateData.put("RS", 60);
        stateData.put("SP", 40);

        // Criar o relatório com dados imutáveis
        return new RelatorioDTO(
                country,
                totalEmployees,
                fileSize,
                runtime,
                cityData,
                stateData
        );
    }
}
