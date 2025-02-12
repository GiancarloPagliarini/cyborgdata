package dev.giancarlo.cyborgdata.service;

import dev.giancarlo.cyborgdata.model.PendenteResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PendenteService {

    @Value("${external.api.url}")
    private String externalApiUrl;

    private final RestTemplate restTemplate;

    public PendenteService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public PendenteResponse getPendenteData() {
        return restTemplate.getForObject(externalApiUrl, PendenteResponse.class);
    }
}
