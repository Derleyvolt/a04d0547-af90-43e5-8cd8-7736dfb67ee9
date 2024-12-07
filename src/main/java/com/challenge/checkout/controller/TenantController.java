package com.challenge.checkout.controller;

import com.challenge.checkout.dto.request.TenantRequestDTO;
import com.challenge.checkout.dto.response.TenantResponseDTO;
import com.challenge.checkout.service.TenantService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tenants")
@Validated
public class TenantController {
    @Autowired
    private TenantService tenantService;

    @GetMapping(value = "/")
    public ResponseEntity<List<TenantResponseDTO>> getTenants() {
        List<TenantResponseDTO> tenants = tenantService.getTenants();
        return ResponseEntity.ok(tenants);
    }

    @PostMapping
    public ResponseEntity<TenantResponseDTO> createTenant(@RequestBody @Valid TenantRequestDTO tenantRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tenantService.createTenant(tenantRequestDTO));
    }

    @PutMapping(value="/{tenantId}")
    public ResponseEntity<TenantResponseDTO> updateTenant(
            @PathVariable Long tenantId,
            @RequestBody @Valid TenantRequestDTO tenantRequestDTO) {
        return ResponseEntity.ok(tenantService.updateTenant(tenantId, tenantRequestDTO));
    }

    @DeleteMapping(value="/{tenantId}")
    public ResponseEntity<Void> createTenant(@PathVariable Long tenantId) {
        tenantService.deleteTenant(tenantId);
        return ResponseEntity.noContent().build();
    }
}
