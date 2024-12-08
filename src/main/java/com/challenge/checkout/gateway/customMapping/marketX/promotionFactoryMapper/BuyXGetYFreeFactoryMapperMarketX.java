package com.challenge.checkout.gateway.customMapping.marketX.promotionFactoryMapper;

import com.challenge.checkout.entity.promotion.BuyXGetYFreePromotion;
import com.challenge.checkout.entity.promotion.Promotion;
import com.challenge.checkout.exception.BadRequestException;
import com.challenge.checkout.gateway.PromotionGatewayMapper;

import java.util.Map;

public class BuyXGetYFreeFactoryMapperMarketX implements PromotionGatewayMapper {
    @Override
    public Promotion map(Map<String, Object> json) {
        try {
            return new BuyXGetYFreePromotion(
                    (String)  json.get("id"),
                    (String)  json.get("type"),
                    (Integer) json.get("required_quantity"),
                    (Integer) json.get("free_quantity")
            );
        } catch (Exception e) {
            throw new BadRequestException("Error parsing promotion from JSON");
        }
    }
}
