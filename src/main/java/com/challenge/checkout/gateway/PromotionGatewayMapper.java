package com.challenge.checkout.gateway;

import com.challenge.checkout.entity.promotion.Promotion;

import java.util.Map;

public interface PromotionGatewayMapper {
    Promotion map(Map<String, Object> json);
}
