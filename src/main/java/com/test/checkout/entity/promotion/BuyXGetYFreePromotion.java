package com.test.checkout.entity.promotion;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.BigInteger;

@Getter
@Setter
@AllArgsConstructor
public class BuyXGetYFreePromotion extends Promotion {
    private int required_qty;
    private int free_qty;

    public BuyXGetYFreePromotion(String id, String type, int required_qty, int free_qty) {
        super(id, type, 2);
        this.required_qty = required_qty;
        this.free_qty = free_qty;
    }

    @Override
    public PromotionResult calculateDiscount(BigDecimal total, BigInteger unitPrice, int quantity) {
        if (quantity >= required_qty) {
            int timesToApply = quantity / required_qty;

            int newQuantity = quantity + timesToApply * free_qty;

            BigDecimal subTotal = new BigDecimal(unitPrice.multiply(BigInteger.valueOf(newQuantity)));

            BigDecimal discount = new BigDecimal(
                    unitPrice.multiply(
                            BigInteger.valueOf(free_qty).multiply(BigInteger.valueOf(timesToApply))
                    )
            );

            return PromotionResult.builder()
                    .subTotal(subTotal.subtract(discount))
                    .price(unitPrice)
                    .quantity(newQuantity)
                    .discount(discount)
                    .build();
        }

        return new PromotionResult(total, unitPrice, quantity, BigDecimal.valueOf(0));
    }

    @Override
    public int getPriority() {
        return priority;
    }
}