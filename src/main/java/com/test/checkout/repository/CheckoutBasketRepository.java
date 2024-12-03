package com.test.checkout.repository;

import com.test.checkout.model.CheckoutBasketModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckoutBasketRepository extends JpaRepository<CheckoutBasketModel, Long> {
}