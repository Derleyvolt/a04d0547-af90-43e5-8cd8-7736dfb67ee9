package com.challenge.checkout.dto.response;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CheckoutBasketModelResponseDTO {
    private List<CheckoutBasketItemModelResponseDTO> items;

    @Min(0)
    private int total;

    @Min(0)
    private BigDecimal totalDiscounts;

    @Min(0)
    private BigDecimal totalPayable;
}
