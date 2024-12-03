package com.challenge.checkout.repository;

import com.challenge.checkout.model.BasketItemModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BasketItemRepository extends JpaRepository<BasketItemModel, Long> {
}
