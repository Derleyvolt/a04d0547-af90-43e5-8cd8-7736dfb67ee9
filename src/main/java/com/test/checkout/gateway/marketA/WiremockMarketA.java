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

    @Getter
    private final String tenantName = "market-a"; // Used to identify correct instance of tenant
                                                  // wiremock by Strategy design pattern

    public List<Map<String, Object>> getProducts(String baseURL) {
        try {
            ResponseEntity<List<Map<String, Object>>> response = restTemplate.exchange(
                    baseURL.concat("products"),
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

    public Map<String, Object> getProductWithPromotion(String baseURL, String productId) {
        try {
            ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                    baseURL.concat("products/").concat(productId),
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
