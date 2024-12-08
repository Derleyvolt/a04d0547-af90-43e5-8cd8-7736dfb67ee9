package com.challenge.checkout.gateway.defaultMapping;

import com.challenge.checkout.gateway.PromotionGatewayMapper;
import com.challenge.checkout.gateway.defaultMapping.promotionFactoryMapper.BuyXGetYFreeFactoryMapper;
import com.challenge.checkout.gateway.defaultMapping.promotionFactoryMapper.FlatPercentFactoryMapper;
import com.challenge.checkout.gateway.defaultMapping.promotionFactoryMapper.QtyBasedPriceOverrideFactoryMapper;

import java.util.HashMap;
import java.util.Map;

public class PromotionFactoryRegistry {
    private static final Map<String, PromotionGatewayMapper> registry = new HashMap<>();

    static {
        registry.put(PromotionEnum.BUY_X_GET_Y_FREE.name(), new BuyXGetYFreeFactoryMapper());
        registry.put(PromotionEnum.FLAT_PERCENT.name(), new FlatPercentFactoryMapper());
        registry.put(PromotionEnum.QTY_BASED_PRICE_OVERRIDE.name(), new QtyBasedPriceOverrideFactoryMapper());
    }

    public static PromotionGatewayMapper getPromotionGatewayMapper(String promotionType) {
        return registry.get(promotionType);
    }
}