package com.challenge.checkout.mapper;

import com.challenge.checkout.dto.request.TenantRequestDTO;
import com.challenge.checkout.dto.response.TenantResponseDTO;
import com.challenge.checkout.model.TenantModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TenantMapper {
    TenantModel toModel(TenantResponseDTO dto);
    TenantResponseDTO toResponseDTO(TenantModel model);

    TenantModel toModel(TenantRequestDTO dto);
    TenantRequestDTO toRequestDTO(TenantModel model);
}
