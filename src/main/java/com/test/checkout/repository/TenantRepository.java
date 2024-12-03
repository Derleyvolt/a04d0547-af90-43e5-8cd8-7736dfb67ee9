package com.test.checkout.repository;

import com.test.checkout.model.CheckoutBasketModel;
import com.test.checkout.model.TenantModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TenantRepository extends JpaRepository<TenantModel, Long> {
    Optional<TenantModel> findByName(String name);
}
