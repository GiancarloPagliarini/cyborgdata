package dev.giancarlo.cyborgdata.repository;

import dev.giancarlo.cyborgdata.model.Processamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProcessamentoRepository extends JpaRepository<Processamento, Long> {
    Optional<Processamento> findByHash(String hash);
}
