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

    @NotNull
    private String productId;

    @NotBlank
    private String productName;

    @Positive
    private int quantity;

    @Min(0)
    private int total;

    @Min(0)
    private BigDecimal totalDiscounts;

    @Min(0)
    private BigDecimal totalPayable;

    @Min(0)
    private BigInteger unitPrice;

    @ManyToOne
    @JoinColumn(name = "checkoutBasketId", nullable = false)
    private CheckoutBasketModel checkoutBasket;
}
