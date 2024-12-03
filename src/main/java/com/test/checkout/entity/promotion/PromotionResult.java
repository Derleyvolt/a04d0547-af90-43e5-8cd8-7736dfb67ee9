package com.test.checkout.entity.promotion;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.BigInteger;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class PromotionResult {
    private BigDecimal subTotal = BigDecimal.valueOf(0);
    private BigInteger price = BigInteger.valueOf(0);
    private int quantity = 0;
    private BigDecimal discount = BigDecimal.valueOf(0);

    private String productName;


    public PromotionResult(BigDecimal total, BigInteger unitPrice, int quantity, BigDecimal bigDecimal) {
        this.price = unitPrice;
        this.subTotal = total;
        this.quantity = quantity;
        this.discount = bigDecimal;
    }

    public void aggregate(PromotionResult promotionResult) {
        this.price = promotionResult.getPrice();
        this.subTotal = promotionResult.getSubTotal();
        this.quantity = promotionResult.getQuantity();
        this.discount = this.discount.add(promotionResult.getDiscount());
    }
}