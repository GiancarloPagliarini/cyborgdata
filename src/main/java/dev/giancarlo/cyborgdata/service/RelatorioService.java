package dev.giancarlo.cyborgdata.service;

import dev.giancarlo.cyborgdata.model.Funcionario;
import dev.giancarlo.cyborgdata.repository.ProcessamentoRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RelatorioService {

    private final ProcessamentoService processamentoService;

    public RelatorioService(ProcessamentoService processamentoService) {
        this.processamentoService = processamentoService;
    }

    public Map<String, Object> gerarRelatorio() {
        List<Funcionario> todosFuncionarios = new ArrayList<>();

        // Extrair funcionários de todos os processos concluídos
        List<String> hashesProcessados = processamentoService.getHashesProcessados();
        for (String hash : hashesProcessados) {
            todosFuncionarios.addAll(processamentoService.extrairFuncionarios(hash));
        }

        // Agrupar por país
        Map<String, List<Funcionario>> funcionariosPorPais = todosFuncionarios.stream()
                .collect(Collectors.groupingBy(Funcionario::getPais));

        List<Map<String, Object>> relatorio = new ArrayList<>();

        for (Map.Entry<String, List<Funcionario>> entry : funcionariosPorPais.entrySet()) {
            String pais = entry.getKey();
            List<Funcionario> funcionarios = entry.getValue();

            Map<String, Object> paisData = new HashMap<>();
            paisData.put("country", pais);
            paisData.put("count_employee", funcionarios.size());

            // Agrupar por cidade e estado
            Map<String, Long> countPorCidade = funcionarios.stream()
                    .collect(Collectors.groupingBy(Funcionario::getCidade, Collectors.counting()));

            Map<String, Long> countPorEstado = funcionarios.stream()
                    .collect(Collectors.groupingBy(Funcionario::getEstado, Collectors.counting()));

            // Faixa etária
            long kids = funcionarios.stream().filter(f -> f.getIdade() < 18).count();
            long young = funcionarios.stream().filter(f -> f.getIdade() >= 18 && f.getIdade() <= 30).count();
            long adult = funcionarios.stream().filter(f -> f.getIdade() > 30).count();

            paisData.put("count_employee_city", countPorCidade);
            paisData.put("count_employee_state", countPorEstado);
            paisData.put("age_distribution", Map.of("kids", kids, "young", young, "adult", adult));

            relatorio.add(paisData);
        }

        return Map.of("empresas", relatorio);
    }
}
