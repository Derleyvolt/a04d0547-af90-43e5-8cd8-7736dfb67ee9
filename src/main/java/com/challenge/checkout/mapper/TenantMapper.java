package com.challenge.checkout.mapper;

import com.challenge.checkout.dto.request.TenantRequestDTO;
import com.challenge.checkout.dto.response.TenantResponseDTO;
import com.challenge.checkout.model.TenantModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses={MappingFormatMapper.class})
public interface TenantMapper {
    TenantResponseDTO toResponseDTO(TenantModel model);

    @Mapping(target="mappingFormat", ignore=true)
    TenantModel toModel(TenantRequestDTO dto);
}
