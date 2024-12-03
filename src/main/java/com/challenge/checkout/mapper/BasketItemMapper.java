package com.challenge.checkout.mapper;

import com.challenge.checkout.dto.request.BasketItemRequestDTO;
import com.challenge.checkout.dto.response.BasketItemResponseDTO;
import com.challenge.checkout.model.BasketItemModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BasketItemMapper {
    @Mapping(target="id", ignore=true)
    @Mapping(target="basket", ignore=true)
    BasketItemModel toModel(BasketItemResponseDTO dto);

    @Mapping(target="id", ignore=true)
    @Mapping(target="basket", ignore=true)
    BasketItemModel toModel(BasketItemRequestDTO dto);

    BasketItemResponseDTO toResponseDTO(BasketItemModel model);
    BasketItemRequestDTO toRequestDTO(BasketItemModel model);
    List<BasketItemModel> toModelList(List<BasketItemRequestDTO> dtoList);
    List<BasketItemRequestDTO> toRequestDTOList(List<BasketItemModel> modelList);
    List<BasketItemResponseDTO> toResponseDTOList(List<BasketItemModel> modelList);
}
