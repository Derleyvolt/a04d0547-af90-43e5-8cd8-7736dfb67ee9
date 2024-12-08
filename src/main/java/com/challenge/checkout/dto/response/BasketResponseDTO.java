package com.challenge.checkout.dto.response;

import com.challenge.checkout.enums.BasketStatusEnum;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BasketResponseDTO {
    @NotNull
    private Long id;

    @NotNull
    private BasketStatusEnum status;

    private List<BasketItemResponseDTO> items;
}
