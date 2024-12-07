package com.challenge.checkout.service;

import com.challenge.checkout.dto.request.TenantRequestDTO;
import com.challenge.checkout.dto.response.TenantResponseDTO;
import com.challenge.checkout.exception.BadRequestException;
import com.challenge.checkout.mapper.TenantMapper;
import com.challenge.checkout.model.TenantModel;
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

    public TenantResponseDTO createTenant(TenantRequestDTO tenantRequestDTO) {
        validateTenant(tenantRequestDTO);

        TenantModel tenant = tenantMapper.toModel(tenantRequestDTO);
        return tenantMapper.toResponseDTO(tenantRepository.save(tenant));
    }

    public TenantResponseDTO updateTenant(Long tenantId, TenantRequestDTO tenantRequestDTO) {
        TenantModel tenantModel = tenantRepository.findById(tenantId).orElseThrow(
                () -> new BadRequestException("Tenant not found")
        );

        tenantRepository.findByName(tenantRequestDTO.getName()).ifPresent(
                tenant -> {
                    if (!tenant.getId().equals(tenantId)) {
                        throw new BadRequestException("Some Tenant already has this name");
                    }
                }
        );

        tenantRepository.findByBaseURL(tenantRequestDTO.getBaseURL()).ifPresent(
                tenant -> {
                    if (!tenant.getId().equals(tenantId)) {
                        throw new BadRequestException("Some Tenant already has this baseURL");
                    }
                }
        );

        tenantModel.setName(tenantRequestDTO.getName());
        tenantModel.setBaseURL(tenantRequestDTO.getBaseURL());

        return tenantMapper.toResponseDTO(tenantRepository.save(tenantModel));
    }

    public List<TenantResponseDTO> getTenants() {
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
