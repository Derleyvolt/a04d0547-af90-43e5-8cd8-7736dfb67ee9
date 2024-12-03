package com.test.checkout.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BasketItemRequestDTO {
    @NotNull(message = "ProductId must not be null")
    private String productId;

    @Positive(message = "Quantity must be greater than zero.")
    private int quantity;

    public void increaseQuantity(int quantity) {
        this.quantity += quantity;
    }
}