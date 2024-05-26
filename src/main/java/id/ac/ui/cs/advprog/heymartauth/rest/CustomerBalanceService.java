package id.ac.ui.cs.advprog.heymartauth.rest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class CustomerBalanceService {
    private final WebClient webClient;

    public CustomerBalanceService(@Value("${app.gateway.order}") String gatewayUrl) {
        this.webClient = WebClient.builder().baseUrl(gatewayUrl + "/customer-balance").build();
    }

    public void createBalance(String token) {
        webClient.post()
                .uri("/create")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .retrieve()
                .toBodilessEntity()
                .block();
    }
}
