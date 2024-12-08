package com.challenge.checkout.repository;

import com.challenge.checkout.model.MappingFormatModel;
import com.challenge.checkout.model.TenantModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TenantRepository extends JpaRepository<TenantModel, Long> {
    Optional<TenantModel> findByName(String name);
    Optional<TenantModel> findByBaseURL(String baseURL);
    boolean existsByName(String name);
    boolean existsByMappingFormat(MappingFormatModel mappingFormat);
}
