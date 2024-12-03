package com.test.checkout.entity.product;

import com.test.checkout.entity.promotion.Promotion;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductWithPromotions extends ProductBase {
    private List<Promotion> promotions;
}