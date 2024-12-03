package com.test.checkout.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CheckoutBasketModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "basketId", nullable = false)
    private BasketModel basket;

    @OneToMany(mappedBy = "checkoutBasket", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CheckoutBasketItemModel> items = new ArrayList<>();

    @Min(0)
    private int total;

    @Min(0)
    private BigDecimal totalDiscounts = BigDecimal.valueOf(0);

    @Min(0)
    private BigDecimal totalPayable = BigDecimal.valueOf(0);
}
