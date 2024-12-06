package com.challenge.checkout.gateway.marketB.promotionFactoryMapper;

import com.challenge.checkout.entity.promotion.FlatPercentPromotion;
import com.challenge.checkout.entity.promotion.Promotion;
import com.challenge.checkout.gateway.PromotionGatewayMapper;

import java.math.BigDecimal;
import java.util.Map;

public class FlatPercentFactoryMapperMarketB implements PromotionGatewayMapper {
    @Override
    public Promotion map(Map<String, Object> json) {
        return new FlatPercentPromotion(
                (String) json.get("id"),
                (String) json.get("type"),
                new BigDecimal(json.get("amount").toString())
        );
    }
}
