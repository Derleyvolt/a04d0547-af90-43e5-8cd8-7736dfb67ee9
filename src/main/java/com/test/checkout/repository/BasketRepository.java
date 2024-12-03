package com.test.checkout.repository;

import com.test.checkout.model.BasketModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BasketRepository extends JpaRepository<BasketModel, Long> {
}