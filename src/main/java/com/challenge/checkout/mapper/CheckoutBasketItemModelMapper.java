package com.challenge.checkout.mapper;

import com.challenge.checkout.dto.response.CheckoutBasketItemModelResponseDTO;
import com.challenge.checkout.dto.response.CheckoutBasketModelResponseDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CheckoutBasketItemModelMapper {
    CheckoutBasketItemModelResponseDTO toResponseDTO(CheckoutBasketModelResponseDTO model);
}