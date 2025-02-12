package dev.giancarlo.cyborgdata.model;

import dev.giancarlo.cyborgdata.model.dto.PendenteDTO;
import java.util.List;

public class PendenteResponse {
    private boolean success;
    private String message;
    private List<PendenteDTO> data;

    // Getters e Setters

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<PendenteDTO> getData() {
        return data;
    }

    public void setData(List<PendenteDTO> data) {
        this.data = data;
    }
}
