package com.challenge.checkout.gateway.customMapping.marketX;

import com.challenge.checkout.gateway.PromotionGatewayMapper;
import com.challenge.checkout.gateway.customMapping.marketX.promotionFactoryMapper.BuyXGetYFreeFactoryMapperMarketX;
import com.challenge.checkout.gateway.customMapping.marketX.promotionFactoryMapper.FlatPercentFactoryMapperMarketX;
import com.challenge.checkout.gateway.customMapping.marketX.promotionFactoryMapper.QtyBasedPriceOverrideFactoryMapperMarketX;

import java.util.HashMap;
import java.util.Map;

public class PromotionFactoryRegistryMarketX {
    private static final Map<String, PromotionGatewayMapper> registry = new HashMap<>();

    static {
        registry.put(PromotionEnumMarketX.BUY_X_GET_Y_FREE_RANDOM_SUFFIX.name(), new BuyXGetYFreeFactoryMapperMarketX());
        registry.put(PromotionEnumMarketX.FLAT_PERCENT_RANDOM_SUFFIX.name(), new FlatPercentFactoryMapperMarketX());
        registry.put(PromotionEnumMarketX.QTY_BASED_PRICE_OVERRIDE_RANDOM_SUFFIX.name(), new QtyBasedPriceOverrideFactoryMapperMarketX());
    }

    public static PromotionGatewayMapper getPromotionGatewayMapper(String promotionType) {
        return registry.get(promotionType);
    }
}