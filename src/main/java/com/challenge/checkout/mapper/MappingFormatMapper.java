package com.challenge.checkout.mapper;

import com.challenge.checkout.dto.request.MappingFormatRequestDTO;
import com.challenge.checkout.dto.response.MappingFormatResponseDTO;
import com.challenge.checkout.model.MappingFormatModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MappingFormatMapper {
    default String toString(MappingFormatModel value) {
        return value != null ? value.getName() : null;
    }

    MappingFormatModel toModel(MappingFormatRequestDTO dto);
    MappingFormatRequestDTO toRequestDTO(MappingFormatModel model);
    MappingFormatResponseDTO toResponseDTO(MappingFormatModel model);
}
