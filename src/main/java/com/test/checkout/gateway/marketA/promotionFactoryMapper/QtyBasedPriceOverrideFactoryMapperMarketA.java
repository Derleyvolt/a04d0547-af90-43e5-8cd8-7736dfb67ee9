package com.test.checkout.gateway.marketA.promotionFactoryMapper;

import com.test.checkout.entity.promotion.Promotion;
import com.test.checkout.entity.promotion.QtyBasedPriceOverridePromotion;
import com.test.checkout.gateway.PromotionGatewayMapper;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Map;

public class QtyBasedPriceOverrideFactoryMapperMarketA implements PromotionGatewayMapper {
    @Override
    public Promotion map(Map<String, Object> json) {
        return new QtyBasedPriceOverridePromotion(
                (String)  json.get("id"),
                (String)  json.get("type"),
                (Integer) json.get("required_qty"),
                new BigInteger(json.get("price").toString())
        );
    }
}
