package com.test.checkout.mapper;

import com.test.checkout.dto.request.BasketRequestDTO;
import com.test.checkout.dto.response.CheckoutBasketModelResponseDTO;
import com.test.checkout.model.BasketModel;
import com.test.checkout.model.CheckoutBasketModel;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CheckoutBasketModelMapper {
    CheckoutBasketModel toModel(CheckoutBasketModelResponseDTO dto);
    CheckoutBasketModelResponseDTO toResponseDTO(CheckoutBasketModel model);
    List<CheckoutBasketModelResponseDTO> toResponseDTOList(List<CheckoutBasketModel> dtoList);
    List<CheckoutBasketModel> toModelList(List<CheckoutBasketModelResponseDTO> modelList);
}