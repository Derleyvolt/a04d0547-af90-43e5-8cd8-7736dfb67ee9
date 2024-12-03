package com.test.checkout.repository;

import com.test.checkout.model.BasketItemModel;
import com.test.checkout.model.BasketModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BasketItemRepository extends JpaRepository<BasketItemModel, Long> {
}
