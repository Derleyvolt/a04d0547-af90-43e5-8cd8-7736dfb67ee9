package com.test.checkout.gateway.marketA.promotionFactoryMapper;

import com.test.checkout.entity.promotion.FlatPercentPromotion;
import com.test.checkout.entity.promotion.Promotion;
import com.test.checkout.gateway.PromotionGatewayMapper;

import java.math.BigDecimal;
import java.util.Map;

public class FlatPercentFactoryMapperMarketA implements PromotionGatewayMapper {
    @Override
    public Promotion map(Map<String, Object> json) {
        return new FlatPercentPromotion(
                (String) json.get("id"),
                (String) json.get("type"),
                new BigDecimal(json.get("amount").toString())
        );
    }
}
