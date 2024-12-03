package com.test.checkout.model;

import com.test.checkout.dto.request.BasketItemRequestDTO;
import com.test.checkout.enums.BasketStatusEnum;
import com.test.checkout.exception.BadRequestException;
import com.test.checkout.exception.product.ProductBadRequestException;
import com.test.checkout.exception.product.ProductNotFoundException;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class BasketModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private Long id;

    @OneToMany(mappedBy = "basket", cascade = CascadeType.ALL, orphanRemoval = true)
//    @JoinColumn(name = "basketId", referencedColumnName = "id")
    private List<BasketItemModel> items = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BasketStatusEnum status;

    public void addOrUpdate(BasketItemModel basketItemModel) {
        Optional<BasketItemModel> optionalBasketItemModel =
                items.stream().filter(basketItemModel::compareProduct).findFirst();

        if (optionalBasketItemModel.isPresent()) {
            // Update quantity
            optionalBasketItemModel.get().setQuantity(basketItemModel.getQuantity());
        } else {
            basketItemModel.setBasket(this);
            items.add(basketItemModel);
        }
    }

    public void addOrUpdate(List<BasketItemModel> basketItemsModel) {
        basketItemsModel.forEach(this::addOrUpdate);
    }

    public void increaseProductQuantity(BasketItemModel basketItemModel) {
        BasketItemModel storedBasketItemModel =
                items.stream().filter(basketItemModel::compareProduct).
                findFirst().orElseThrow(() -> new ProductNotFoundException(
                            String.format("Product %s Not Found in Basket", basketItemModel.getProductId())
                        )
                );

        storedBasketItemModel.setQuantity(
                basketItemModel.getQuantity() + storedBasketItemModel.getQuantity()
        );
    }

    public void increaseProductQuantity(List<BasketItemModel> basketItemsModel) {
        for (BasketItemModel basketItemModel : basketItemsModel) {
            this.increaseProductQuantity(basketItemModel);
        }
    }

    public void decreaseProductQuantity(BasketItemModel basketItemModel) {
        BasketItemModel storedBasketItemModel =
                items.stream().filter(basketItemModel::compareProduct).
                        findFirst().orElseThrow(() -> new ProductNotFoundException(
                                        String.format("Product %s Not Found in Basket", basketItemModel.getProductId())
                                )
                        );

        int newQuantity = storedBasketItemModel.getQuantity() - basketItemModel.getQuantity();

        if (newQuantity <= 0) {
            throw new BadRequestException(
                    String.format("Product %s need has positive quantity", storedBasketItemModel.getProductId())
            );
        }

        storedBasketItemModel.setQuantity(newQuantity);
    }

    public void decreaseProductQuantity(List<BasketItemModel> basketItemsModel) {
        for (BasketItemModel basketItemModel : basketItemsModel) {
            this.decreaseProductQuantity(basketItemModel);
        }
    }
}