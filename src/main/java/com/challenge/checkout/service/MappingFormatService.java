package com.challenge.checkout.service;

import com.challenge.checkout.dto.request.MappingFormatRequestDTO;
import com.challenge.checkout.dto.response.MappingFormatResponseDTO;
import com.challenge.checkout.exception.BadRequestException;
import com.challenge.checkout.mapper.MappingFormatMapper;
import com.challenge.checkout.model.MappingFormatModel;
import com.challenge.checkout.repository.MappingFormatRepository;
import com.challenge.checkout.repository.TenantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class MappingFormatService {
    @Autowired
    private MappingFormatRepository mappingFormatRepository;

    @Autowired
    private MappingFormatMapper mappingFormatMapper;

    @Autowired
    private TenantRepository tenantRepository;

    private MappingFormatModel getMappingFormatOrException(Long mappingFormatId) {
        return mappingFormatRepository.findById(mappingFormatId)
               .orElseThrow(() -> new BadRequestException("Mapping Format not found"));
    }

    public MappingFormatResponseDTO createMappingFormat(MappingFormatRequestDTO dto) {
        // Check if the mapping format exists
        if (mappingFormatRepository.existsByName(dto.getName())) {
            throw new BadRequestException("Mapping Format already exists");
        }

        MappingFormatModel mappingFormatModel = mappingFormatMapper.toModel(dto);
        return mappingFormatMapper.toResponseDTO(mappingFormatRepository.save(mappingFormatModel));
    }

    public List<MappingFormatResponseDTO> getAllMappingFormats() {
        return mappingFormatRepository.findAll()
                .stream()
                .map(mappingFormatMapper::toResponseDTO)
                .toList();
    }

    public void deleteMappingFormat(Long mappingFormatId) {
        MappingFormatModel mappingFormatModel = getMappingFormatOrException(mappingFormatId);

        if (tenantRepository.existsByMappingFormat(mappingFormatModel)) {
            throw new BadRequestException("Mapping Format is still used by a tenant");
        }

        mappingFormatRepository.delete(mappingFormatModel);
    }

    public MappingFormatResponseDTO updateMappingFormat(Long mappingFormatId, MappingFormatRequestDTO dto) {
        MappingFormatModel mappingFormatModel = getMappingFormatOrException(mappingFormatId);

        if (!Objects.equals(dto.getName(), mappingFormatModel.getName())
            && mappingFormatRepository.existsByName(dto.getName())
        ) {
            throw new BadRequestException("Mapping Format already exists");
        }

        mappingFormatModel.setName(dto.getName());
        return mappingFormatMapper.toResponseDTO(mappingFormatRepository.save(mappingFormatModel));
    }
}
