package dev.giancarlo.cyborgdata.config;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.cert.X509Certificate;
import java.security.NoSuchAlgorithmException;
import java.security.KeyManagementException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.client.SimpleClientHttpRequestFactory;

@Configuration
public class SSLConfig {

    @Bean
    public RestTemplate restTemplate() throws NoSuchAlgorithmException, KeyManagementException {
        // Criando um TrustManager que não valida certificados
        TrustManager[] trustAllCertificates = new TrustManager[]{
                new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }

                    public void checkClientTrusted(X509Certificate[] certs, String authType) {
                    }

                    public void checkServerTrusted(X509Certificate[] certs, String authType) {
                    }
                }
        };

        // Instalando o TrustManager que ignora a validação do certificado
        SSLContext sc = SSLContext.getInstance("TLS");
        sc.init(null, trustAllCertificates, new java.security.SecureRandom());
        SSLContext.setDefault(sc);

        // Retorna o RestTemplate simples
        return new RestTemplate(new SimpleClientHttpRequestFactory());
    }
}
