package com.challenge.checkout.gateway;

import com.challenge.checkout.exception.BadRequestException;
import com.challenge.checkout.model.MappingFormatModel;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class GatewayMarketStrategy {
    private final Map<String, WiremockGateway> gateways = new HashMap<>();

    public GatewayMarketStrategy(List<WiremockGateway> gatewayList) {
        gatewayList.forEach(gateway -> {
            this.gateways.put(gateway.getMappingFormat().name(), gateway);
        });
    }

    public WiremockGateway getMarketGateway(MappingFormatModel marketMappingFormat) {

        // If that gateway not exists in gateways map, so no implementation
        // for the given mapping format exists. Throw a bad request exception.
        if (!this.gateways.containsKey(marketMappingFormat.getName())) {
            throw new BadRequestException(
                String.format(
                        "No implementation exists for the mapping format: %s.",
                        marketMappingFormat.getName())
                );
        }

        return this.gateways.get(marketMappingFormat.getName());
    }
}