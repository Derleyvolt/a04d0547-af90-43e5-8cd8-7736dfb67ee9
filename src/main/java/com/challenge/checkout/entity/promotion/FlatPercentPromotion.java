package com.challenge.checkout.entity.promotion;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.BigInteger;

@Getter
@Setter
@AllArgsConstructor
public class FlatPercentPromotion extends Promotion {
    private BigDecimal amount;

    public FlatPercentPromotion(String id, String type, BigDecimal amount) {
        super(id, type, 3);
        this.amount = amount;
    }

    @Override
    public PromotionResult calculateDiscount(BigDecimal total, BigInteger unitPrice, int quantity) {
        if (amount.equals(BigDecimal.ZERO)) {
            return new PromotionResult(total, unitPrice, quantity, BigDecimal.valueOf(0));
        }

        BigDecimal discount = new BigDecimal(
                unitPrice.multiply(BigInteger.valueOf(quantity))
        ).multiply(amount).divide(BigDecimal.valueOf(100));

        return PromotionResult.builder()
                .subTotal(total.subtract(discount))
                .price(unitPrice)
                .quantity(quantity)
                .discount(discount)
                .build();
    }

    @Override
    public int getPriority() {
        return priority;
    }
}