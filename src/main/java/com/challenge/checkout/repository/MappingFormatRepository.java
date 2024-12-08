package com.challenge.checkout.repository;

import com.challenge.checkout.model.CheckoutBasketModel;
import com.challenge.checkout.model.MappingFormatModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MappingFormatRepository extends JpaRepository<MappingFormatModel, Long> {
    Optional<MappingFormatModel> findByName(String mappingFormatName);
    boolean existsByName(String mappingFormatName);
}