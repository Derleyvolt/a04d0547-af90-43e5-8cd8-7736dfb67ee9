package com.challenge.checkout.gateway.coreMapping.promotionFactoryMapper;

import com.challenge.checkout.entity.promotion.FlatPercentPromotion;
import com.challenge.checkout.entity.promotion.Promotion;
import com.challenge.checkout.exception.BadRequestException;
import com.challenge.checkout.gateway.PromotionGatewayMapper;

import java.math.BigDecimal;
import java.util.Map;

public class FlatPercentFactoryMapper implements PromotionGatewayMapper {
    @Override
    public Promotion map(Map<String, Object> json) {
        try {
            return new FlatPercentPromotion(
                    (String) json.get("id"),
                    (String) json.get("type"),
                    new BigDecimal(json.get("amount").toString())
            );
        } catch (Exception e) {
            throw new BadRequestException("Error parsing promotion from JSON");
        }
    }
}
