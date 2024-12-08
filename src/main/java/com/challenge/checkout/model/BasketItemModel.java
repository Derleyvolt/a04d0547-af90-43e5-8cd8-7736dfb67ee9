package com.challenge.checkout.model;

import jakarta.persistence.*;
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

    private String productId;

    private String productName;

    private int quantity;

    @ManyToOne
    @JoinColumn(name = "basketId", nullable = false)
    private BasketModel basket;

    public boolean compareProduct(BasketItemModel basketItemModel) {
        return Objects.equals(this.getProductId(), basketItemModel.getProductId());
    }
}