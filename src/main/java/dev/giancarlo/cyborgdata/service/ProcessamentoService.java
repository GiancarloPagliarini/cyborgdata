package dev.giancarlo.cyborgdata.service;

import dev.giancarlo.cyborgdata.dto.ProcessamentoStatusDTO;
import dev.giancarlo.cyborgdata.model.Processamento;
import dev.giancarlo.cyborgdata.repository.ProcessamentoRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class ProcessamentoService {

    private final ProcessamentoRepository repository;

    public ProcessamentoService(ProcessamentoRepository repository) {
        this.repository = repository;
    }

    @Async
    public void iniciarProcessamento(String hash, String company) {
        Processamento processamento = new Processamento();
        processamento.setHash(hash);
        processamento.setCompany(company);
        processamento.setStatus("Running");
        processamento.setPercentProcessado(0);
        processamento.setTempoExecucao(0);
        repository.save(processamento);

        try {
            for (int i = 1; i <= 100; i += 10) {
                Thread.sleep(3000);  // Simulando tempo de execução
                processamento.setPercentProcessado(i);
                processamento.setTempoExecucao(i / 10);
                repository.save(processamento);
            }
            processamento.setStatus("Completed");
            repository.save(processamento);

        } catch (InterruptedException e) {
            processamento.setStatus("Error");
            repository.save(processamento);
        }
    }

    public ProcessamentoStatusDTO consultarProcessamento(String hash) {
        Processamento processamento = repository.findByHash(hash)
                .orElseThrow(() -> new RuntimeException("Processo não encontrado"));

        return new ProcessamentoStatusDTO(
                processamento.getCompany(),
                processamento.getTempoExecucao() + " minutos",
                processamento.getPercentProcessado() + "%",
                processamento.getStatus()
        );
    }
    }

}
