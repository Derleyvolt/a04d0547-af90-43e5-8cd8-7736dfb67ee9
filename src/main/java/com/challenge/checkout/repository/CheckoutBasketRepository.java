package com.challenge.checkout.repository;

import com.challenge.checkout.model.BasketModel;
import com.challenge.checkout.model.CheckoutBasketModel;
import org.hibernate.annotations.Check;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CheckoutBasketRepository extends JpaRepository<CheckoutBasketModel, Long> {
    Optional<CheckoutBasketModel> findByBasket(BasketModel basketModel);
}