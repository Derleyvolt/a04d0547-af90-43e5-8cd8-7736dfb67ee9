package com.challenge.checkout.gateway.marketB.promotionFactoryMapper;

import com.challenge.checkout.entity.promotion.Promotion;
import com.challenge.checkout.entity.promotion.QtyBasedPriceOverridePromotion;
import com.challenge.checkout.gateway.PromotionGatewayMapper;

import java.math.BigInteger;
import java.util.Map;

public class QtyBasedPriceOverrideFactoryMapperMarketB implements PromotionGatewayMapper {
    @Override
    public Promotion map(Map<String, Object> json) {
        return new QtyBasedPriceOverridePromotion(
                (String)  json.get("id"),
                (String)  json.get("type"),
                (Integer) json.get("required_quantity"),
                new BigInteger(json.get("unit_price").toString())
        );
    }
}
