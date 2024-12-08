package com.challenge.checkout.entity.promotion;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.BigInteger;

@Getter
@Setter
@AllArgsConstructor
public class QtyBasedPriceOverridePromotion extends Promotion {
    private int required_qty;
    private BigInteger price;

    public QtyBasedPriceOverridePromotion(String id, String type, int required_qty, BigInteger price) {
        super(id, type, 1);
        this.required_qty = required_qty;
        this.price = price;
    }

    @Override
    public PromotionResult calculateDiscount(BigDecimal total, BigInteger unitPrice, int quantity) {
        if (quantity >= required_qty) {
            int timesToApply   = quantity / required_qty;
            int remainQuantity = quantity % required_qty;

            BigInteger subTotal = price
                    .multiply(BigInteger.valueOf(timesToApply))
                    .add(BigInteger.valueOf((long) remainQuantity * unitPrice.intValue()));

            return PromotionResult.builder()
                    .subTotal(new BigDecimal(subTotal))
                    .price(unitPrice)
                    .quantity(quantity)
                    .discount(total.subtract(new BigDecimal(subTotal)))
                    .build();
        }
        return new PromotionResult(total, unitPrice, quantity, BigDecimal.valueOf(0));
    }
}