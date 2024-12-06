package com.challenge.checkout.mapper;

import com.challenge.checkout.dto.response.BasketResponseDTO;
import com.challenge.checkout.model.BasketModel;
import com.challenge.checkout.dto.request.BasketRequestDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses={BasketItemMapper.class})
public interface BasketMapper {
    BasketResponseDTO toResponseDTO(BasketModel basketModel);
    List<BasketResponseDTO> toResponseDTOList(List<BasketModel> basketModels);
}