package dev.giancarlo.cyborgdata.model;

import java.time.Duration;
import java.time.LocalDateTime;

public class ProcessamentoStatus {
    private String company;
    private LocalDateTime startTime;
    private int percentual;
    private String status; // Pending, Running, Completed

    public ProcessamentoStatus(String company) {
        this.company = company;
        this.startTime = LocalDateTime.now();
        this.percentual = 0;
        this.status = "Pending";
    }

    public String getRuntime() {
        return Duration.between(startTime, LocalDateTime.now()).toMinutes() + " minutos";
    }

    public String getCompany() {
        return company;
    }

    public int getPercentual() {
        return percentual;
    }

    public void setPercentual(int percentual) {
        this.percentual = percentual;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}