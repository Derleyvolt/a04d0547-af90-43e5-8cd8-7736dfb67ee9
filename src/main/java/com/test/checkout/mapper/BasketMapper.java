package com.test.checkout.mapper;

import com.test.checkout.dto.response.BasketResponseDTO;
import com.test.checkout.model.BasketModel;
import com.test.checkout.dto.request.BasketRequestDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses={BasketItemMapper.class})
public interface BasketMapper {
    BasketModel toModel(BasketRequestDTO dto);
    BasketResponseDTO toResponseDTO(BasketModel basketModel);
    List<BasketResponseDTO> toResponseDTOList(List<BasketModel> basketModels);
}