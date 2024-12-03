package com.test.checkout.gateway;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class GatewayMarketStrategy {
    private final Map<String, WiremockGateway> gateways = new HashMap<>();

    public GatewayMarketStrategy(List<WiremockGateway> gatewayList) {
        gatewayList.forEach(gateway -> {
            this.gateways.put(gateway.getTenantName(), gateway);
        });
    }

    public WiremockGateway getMarketGateway(String marketId) {
        return this.gateways.get(marketId);
    }
}
