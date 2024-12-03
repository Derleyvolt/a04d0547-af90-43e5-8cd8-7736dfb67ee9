package com.challenge.checkout.gateway.marketA.promotionFactoryMapper;

import com.challenge.checkout.entity.promotion.BuyXGetYFreePromotion;
import com.challenge.checkout.entity.promotion.Promotion;
import com.challenge.checkout.gateway.PromotionGatewayMapper;

import java.util.Map;

public class BuyXGetYFreeFactoryMapperMarketA implements PromotionGatewayMapper {
    @Override
    public Promotion map(Map<String, Object> json) {
        return new BuyXGetYFreePromotion(
                (String)  json.get("id"),
                (String)  json.get("type"),
                (Integer) json.get("required_qty"),
                (Integer) json.get("free_qty")
        );
    }
}
