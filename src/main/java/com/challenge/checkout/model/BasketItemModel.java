package com.challenge.checkout.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BasketItemModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String productId;

    @NotBlank
    private String productName;

    @Positive
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "basketId", nullable = false)
    private BasketModel basket;

    public boolean compareProduct(BasketItemModel basketItemModel) {
        return Objects.equals(this.getProductId(), basketItemModel.getProductId());
    }
}