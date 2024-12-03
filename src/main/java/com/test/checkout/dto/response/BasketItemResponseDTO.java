package com.test.checkout.dto.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BasketItemResponseDTO {
    @NotNull
    private String productId;

    @Positive
    private int quantity;

    @NotBlank
    private String productName;

    public void increaseQuantity(int quantity) {
        this.quantity += quantity;
    }
}
