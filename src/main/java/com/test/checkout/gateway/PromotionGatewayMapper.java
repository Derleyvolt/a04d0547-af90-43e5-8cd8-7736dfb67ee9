package com.test.checkout.gateway;

import com.test.checkout.entity.promotion.Promotion;

import java.util.Map;

public interface PromotionGatewayMapper {
    Promotion map(Map<String, Object> json);
}
