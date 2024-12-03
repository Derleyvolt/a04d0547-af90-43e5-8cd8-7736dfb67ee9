package com.test.checkout.gateway.marketA;

import com.test.checkout.exception.BadRequestException;
import com.test.checkout.exception.product.ProductNotFoundException;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Component
public class WiremockMarketA {
    @Autowired
    private RestTemplate restTemplate;

    @Value("${tenants.MarketA.url}")
    private String URL;

    @Value("${tenants.MarketA.name}")
    @Getter
    private String name;

    public List<Map<String, Object>> getProducts() {
        try {
            ResponseEntity<List<Map<String, Object>>> response = restTemplate.exchange(
                    URL,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<>() {}
            );

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                return response.getBody();
            }

            throw new BadRequestException("Failure to fetch products from Market");
        } catch (RestClientException e) {
            throw new RestClientException("Failed to connect to Market API");
        }
    }

    public Map<String, Object> getProductWithPromotion(String productId) {
        try {
            ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                    URL.concat("/").concat(productId),
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<>() {}
            );

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                return response.getBody();
            }

            throw new ProductNotFoundException(String.format("Product with id %s do not exists in market", productId));
        } catch (RestClientException e) {
            throw new RestClientException("Failed to connect to Market API");
        }
    }
}
