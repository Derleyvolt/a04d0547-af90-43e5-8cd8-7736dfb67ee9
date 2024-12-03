package com.test.checkout.dto.response;

import com.test.checkout.model.CheckoutBasketModel;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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
public class CheckoutBasketItemModelResponseDTO {
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
}
