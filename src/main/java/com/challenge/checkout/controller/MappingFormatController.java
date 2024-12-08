package com.challenge.checkout.controller;

import com.challenge.checkout.dto.request.MappingFormatRequestDTO;
import com.challenge.checkout.dto.response.MappingFormatResponseDTO;
import com.challenge.checkout.service.MappingFormatService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mapping-format")
public class MappingFormatController {
    @Autowired
    private MappingFormatService mappingFormatService;

    @GetMapping
    public ResponseEntity<List<MappingFormatResponseDTO>> getAllMappingFormats() {
        List<MappingFormatResponseDTO> mappingFormatResponseDTOList = mappingFormatService.getAllMappingFormats();
        return ResponseEntity.ok(mappingFormatResponseDTOList);
    }

    @PostMapping
    public ResponseEntity<MappingFormatResponseDTO> createMappingFormat(@RequestBody @Valid MappingFormatRequestDTO mappingFormatRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(mappingFormatService
                        .createMappingFormat(mappingFormatRequestDTO));
    }

    @PutMapping(value="/{mappingFormat}")
    public ResponseEntity<MappingFormatResponseDTO> updateMappingFormat(
            @PathVariable Long mappingFormat,
            @RequestBody @Valid MappingFormatRequestDTO mappingFormatRequestDTO) {
        return ResponseEntity.ok(mappingFormatService.updateMappingFormat(mappingFormat, mappingFormatRequestDTO));
    }

    @DeleteMapping(value="/{mappingFormat}")
    public ResponseEntity<Void> deleteMappingFormat(@PathVariable Long mappingFormat) {
        mappingFormatService.deleteMappingFormat(mappingFormat);
        return ResponseEntity.noContent().build();
    }
}
