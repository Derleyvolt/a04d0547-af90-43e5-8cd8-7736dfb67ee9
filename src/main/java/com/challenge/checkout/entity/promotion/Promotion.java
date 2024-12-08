package com.challenge.checkout.entity.promotion;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.BigInteger;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class Promotion implements Comparable<Promotion> {
    protected String id;
    protected String type;

    @JsonIgnore
    protected int priority;

    public abstract PromotionResult calculateDiscount(BigDecimal total, BigInteger unitPrice, int quantity);
    public abstract int getPriority();

    @Override public int compareTo(Promotion other) {
        return Integer.compare(this.priority, other.priority);
    }
}