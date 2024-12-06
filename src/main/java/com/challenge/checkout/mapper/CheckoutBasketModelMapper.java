package com.challenge.checkout.mapper;

import com.challenge.checkout.dto.response.CheckoutBasketModelResponseDTO;
import com.challenge.checkout.model.CheckoutBasketModel;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CheckoutBasketModelMapper {
    CheckoutBasketModel toModel(CheckoutBasketModelResponseDTO dto);
    CheckoutBasketModelResponseDTO toResponseDTO(CheckoutBasketModel model);
}