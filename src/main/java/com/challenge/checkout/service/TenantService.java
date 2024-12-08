package com.challenge.checkout.service;

import com.challenge.checkout.dto.request.TenantRequestDTO;
import com.challenge.checkout.dto.response.TenantResponseDTO;
import com.challenge.checkout.exception.BadRequestException;
import com.challenge.checkout.mapper.TenantMapper;
import com.challenge.checkout.model.MappingFormatModel;
import com.challenge.checkout.model.TenantModel;
import com.challenge.checkout.repository.MappingFormatRepository;
import com.challenge.checkout.repository.TenantRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TenantService {
    @Autowired
    private TenantRepository tenantRepository;

    @Autowired
    private MappingFormatRepository mappingFormatRepository;

    @Autowired
    private TenantMapper tenantMapper;

    public void validateTenant(TenantRequestDTO tenantRequestDTO) {
        if (tenantRepository.findByName(tenantRequestDTO.getName()).isPresent()) {
            throw new BadRequestException(
                    String.format("Tenant %s already exists", tenantRequestDTO.getName())
            );
        }

        if (tenantRepository.findByBaseURL(tenantRequestDTO.getBaseURL()).isPresent()) {
            throw new BadRequestException("This base URL already exists");
        }
    }

    private MappingFormatModel getMappingFormatOrException(String mappingFormatName) {
        return mappingFormatRepository.findByName(mappingFormatName).orElseThrow(
                () -> new BadRequestException(String.format("Mapping format %s not found.", mappingFormatName))
        );
    }

    public TenantResponseDTO createTenant(TenantRequestDTO tenantRequestDTO) {
        validateTenant(tenantRequestDTO);

        MappingFormatModel mappingFormat =
                getMappingFormatOrException(tenantRequestDTO.getMappingFormat());

        TenantModel tenant = tenantMapper.toModel(tenantRequestDTO);

        tenant.setMappingFormat(mappingFormat);
        return tenantMapper.toResponseDTO(tenantRepository.save(tenant));
    }

    public TenantResponseDTO updateTenant(Long tenantId, TenantRequestDTO tenantRequestDTO) {
        TenantModel tenantModel = tenantRepository.findById(tenantId).orElseThrow(
                () -> new BadRequestException("Tenant not found")
        );

        MappingFormatModel mappingFormat =
                getMappingFormatOrException(tenantRequestDTO.getMappingFormat());

        if (!tenantRequestDTO.getName().equals(tenantModel.getName())
            && tenantRepository.existsByName(tenantRequestDTO.getName())
        ) {
            throw new BadRequestException("Already exists tenant with this name");
        }

        if (!tenantRequestDTO.getBaseURL().equals(tenantModel.getBaseURL())
            && tenantRepository.findByBaseURL(tenantRequestDTO.getBaseURL()).isPresent()
        ) {
            throw new BadRequestException("Already exists tenant with this baseURL");
        }

        tenantModel.setMappingFormat(mappingFormat);
        tenantModel.setName(tenantRequestDTO.getName());
        tenantModel.setBaseURL(tenantRequestDTO.getBaseURL());

        return tenantMapper.toResponseDTO(tenantRepository.save(tenantModel));
    }

    public List<TenantResponseDTO> getAllTenants() {
        List<TenantModel> tenantModel = tenantRepository.findAll();
        return tenantModel.stream().map(tenantMapper::toResponseDTO).toList();
    }

    @Transactional
    public void deleteTenant(Long tenantId) {
        tenantRepository.findById(tenantId).orElseThrow(
                () -> new BadRequestException("Tenant not found")
        );

        tenantRepository.deleteById(tenantId);
    }
}
