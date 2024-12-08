package com.challenge.checkout.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;
import java.math.BigInteger;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CheckoutBasketItemModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productId;
    private String productName;
    private int quantity;
    private int total;
    private BigDecimal totalDiscounts;
    private BigDecimal totalPayable;
    private BigInteger unitPrice;

    @ManyToOne
    @JoinColumn(name = "checkoutBasketId", nullable = false)
    private CheckoutBasketModel checkoutBasket;
}
