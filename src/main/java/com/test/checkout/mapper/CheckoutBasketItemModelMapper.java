package com.test.checkout.mapper;

import com.test.checkout.dto.response.BasketItemResponseDTO;
import com.test.checkout.dto.response.CheckoutBasketItemModelResponseDTO;
import com.test.checkout.dto.response.CheckoutBasketModelResponseDTO;
import com.test.checkout.model.BasketItemModel;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CheckoutBasketItemModelMapper {
    CheckoutBasketItemModelResponseDTO toResponseDTO(CheckoutBasketModelResponseDTO model);
    CheckoutBasketModelResponseDTO toModel(CheckoutBasketItemModelResponseDTO dto);
    List<CheckoutBasketItemModelResponseDTO> toResponseDTOList(List<CheckoutBasketModelResponseDTO> dtoList);
    List<CheckoutBasketItemModelResponseDTO> toModel(List<CheckoutBasketModelResponseDTO> modelList);
}