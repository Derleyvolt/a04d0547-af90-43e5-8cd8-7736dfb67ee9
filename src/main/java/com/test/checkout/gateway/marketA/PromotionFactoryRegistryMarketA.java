package com.test.checkout.gateway.marketA;

import com.test.checkout.gateway.PromotionGatewayMapper;
import com.test.checkout.gateway.marketA.promotionFactoryMapper.BuyXGetYFreeFactoryMapperMarketA;
import com.test.checkout.gateway.marketA.promotionFactoryMapper.FlatPercentFactoryMapperMarketA;
import com.test.checkout.gateway.marketA.promotionFactoryMapper.QtyBasedPriceOverrideFactoryMapperMarketA;

import java.util.HashMap;
import java.util.Map;

public class PromotionFactoryRegistryMarketA {
    private static final Map<String, PromotionGatewayMapper> registry = new HashMap<>();

    static {
        registry.put(PromotionMarketAEnum.BUY_X_GET_Y_FREE.name(), new BuyXGetYFreeFactoryMapperMarketA());
        registry.put(PromotionMarketAEnum.FLAT_PERCENT.name(), new FlatPercentFactoryMapperMarketA());
        registry.put(PromotionMarketAEnum.QTY_BASED_PRICE_OVERRIDE.name(), new QtyBasedPriceOverrideFactoryMapperMarketA());
    }

    public static PromotionGatewayMapper getPromotionGatewayMapper(String promotionType) {
        return registry.get(promotionType);
    }
}

