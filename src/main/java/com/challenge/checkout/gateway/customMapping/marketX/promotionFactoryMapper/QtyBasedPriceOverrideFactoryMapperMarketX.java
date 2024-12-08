package com.challenge.checkout.gateway.customMapping.marketX.promotionFactoryMapper;

import com.challenge.checkout.entity.promotion.Promotion;
import com.challenge.checkout.entity.promotion.QtyBasedPriceOverridePromotion;
import com.challenge.checkout.exception.BadRequestException;
import com.challenge.checkout.gateway.PromotionGatewayMapper;

import java.math.BigInteger;
import java.util.Map;

public class QtyBasedPriceOverrideFactoryMapperMarketX implements PromotionGatewayMapper {
    @Override
    public Promotion map(Map<String, Object> json) {
        try {
            return new QtyBasedPriceOverridePromotion(
                    (String)  json.get("id"),
                    (String)  json.get("type"),
                    (Integer) json.get("required_quantity"),
                    new BigInteger(json.get("unit_price").toString())
            );
        } catch (Exception e) {
            throw new BadRequestException("Error parsing promotion from JSON");
        }
    }
}