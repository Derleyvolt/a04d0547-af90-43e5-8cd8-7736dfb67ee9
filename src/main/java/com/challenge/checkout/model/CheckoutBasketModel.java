package com.challenge.checkout.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
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

    @OneToMany(
            mappedBy = "checkoutBasket",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<CheckoutBasketItemModel> items = new ArrayList<>();

    private int total;
    private BigDecimal totalDiscounts = BigDecimal.valueOf(0);
    private BigDecimal totalPayable   = BigDecimal.valueOf(0);
}
