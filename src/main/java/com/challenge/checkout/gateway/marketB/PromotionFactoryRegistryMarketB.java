package com.challenge.checkout.gateway.marketB;

import com.challenge.checkout.gateway.PromotionGatewayMapper;
import com.challenge.checkout.gateway.marketB.promotionFactoryMapper.BuyXGetYFreeFactoryMapperMarketB;
import com.challenge.checkout.gateway.marketB.promotionFactoryMapper.FlatPercentFactoryMapperMarketB;
import com.challenge.checkout.gateway.marketB.promotionFactoryMapper.QtyBasedPriceOverrideFactoryMapperMarketB;

import java.util.HashMap;
import java.util.Map;

public class PromotionFactoryRegistryMarketB {
    private static final Map<String, PromotionGatewayMapper> registry = new HashMap<>();

    static {
        registry.put(PromotionMarketBEnum.BUY_X_GET_Y_FREE_RANDOM_SUFFIX.name(), new BuyXGetYFreeFactoryMapperMarketB());
        registry.put(PromotionMarketBEnum.FLAT_PERCENT_RANDOM_SUFFIX.name(), new FlatPercentFactoryMapperMarketB());
        registry.put(PromotionMarketBEnum.QTY_BASED_PRICE_OVERRIDE_RANDOM_SUFFIX.name(), new QtyBasedPriceOverrideFactoryMapperMarketB());
    }

    public static PromotionGatewayMapper getPromotionGatewayMapper(String promotionType) {
        return registry.get(promotionType);
    }
}