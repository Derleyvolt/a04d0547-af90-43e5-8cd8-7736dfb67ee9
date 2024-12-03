package com.test.checkout.dto.response;

import com.test.checkout.enums.BasketStatusEnum;
import com.test.checkout.model.BasketModel;
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
