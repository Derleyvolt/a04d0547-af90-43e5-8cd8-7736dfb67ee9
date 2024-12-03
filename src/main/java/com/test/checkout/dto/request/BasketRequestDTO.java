package com.test.checkout.dto.request;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BasketRequestDTO {
    private Long id;

    @Valid
    private List<BasketItemRequestDTO> items;
}
