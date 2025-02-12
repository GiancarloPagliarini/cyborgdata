package dev.giancarlo.cyborgdata.service;

import dev.giancarlo.cyborgdata.model.ProcessamentoStatus;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ProcessamentoService {
    private final Map<String, ProcessamentoStatus> processamentoMap = new ConcurrentHashMap<>();

    public void iniciarProcessamento(String hash, String company) {
        processamentoMap.put(hash, new ProcessamentoStatus(company));
    }

    public ProcessamentoStatus getProcessamentoStatus(String hash) {
        return processamentoMap.get(hash);
    }
}
