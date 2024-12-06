package com.challenge.checkout.repository;

import com.challenge.checkout.model.BasketModel;
import com.challenge.checkout.model.TenantModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BasketRepository extends JpaRepository<BasketModel, Long> {
    Optional<BasketModel> findByIdAndTenant(Long id, TenantModel tenant);
    List<BasketModel> findByTenant(TenantModel tenant);
}