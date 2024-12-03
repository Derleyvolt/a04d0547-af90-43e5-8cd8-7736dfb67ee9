package com.test.checkout.controller;

import com.test.checkout.dto.request.BasketItemRequestDTO;
import com.test.checkout.dto.response.BasketResponseDTO;
import com.test.checkout.dto.response.CheckoutBasketModelResponseDTO;
import com.test.checkout.model.BasketModel;
import com.test.checkout.service.BasketService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/baskets")
@Validated
public class BasketController {
    @Autowired
    private BasketService basketService;

    @GetMapping
    public ResponseEntity<List<BasketResponseDTO>> getALLBaskets() {
        List<BasketResponseDTO> baskets = basketService.getAllBasket();

        return ResponseEntity.ok(baskets);
    }

    @PostMapping
    public ResponseEntity<BasketResponseDTO> createBasket(
            @RequestBody @Valid List<BasketItemRequestDTO> basketProducts
    ) {
        BasketResponseDTO basket = basketService.createBasket(basketProducts);

        URI location = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(basket.getId())
            .toUri();

        return ResponseEntity.created(location).body(basket);
    }

    @GetMapping(value = "/{basketId}")
    public ResponseEntity<BasketResponseDTO> detailBasket(@PathVariable Long basketId) {
        BasketResponseDTO basketModel = basketService.detailBasket(basketId);
        return ResponseEntity.ok().body(basketModel);
    }

    @PutMapping(value = "/{basketId}/items/increase-quantity")
    public ResponseEntity<BasketResponseDTO> increaseItemQuantity(
            @PathVariable Long basketId,
            @RequestBody @Valid List<BasketItemRequestDTO> basketItemRequestDTOList
    ) {
        BasketResponseDTO basketResponseDTO = basketService.increaseItemsQuantity(basketId, basketItemRequestDTOList);
        return ResponseEntity.ok().body(basketResponseDTO);
    }

    @PutMapping(value = "/{basketId}/items/decrease-quantity")
    public ResponseEntity<BasketResponseDTO> decreaseItemQuantity(
            @PathVariable Long basketId,
            @RequestBody @Valid List<BasketItemRequestDTO> basketItemRequestDTOList
    ) {
        BasketResponseDTO basketResponseDTO = basketService.decreaseItemsQuantity(basketId, basketItemRequestDTOList);
        return ResponseEntity.ok().body(basketResponseDTO);
    }

    @PutMapping(value = "/{basketId}/items/add-or-update")
    public ResponseEntity<BasketResponseDTO> addOrUpdateItems(
            @PathVariable Long basketId,
            @RequestBody @Valid List<BasketItemRequestDTO> basketItemRequestDTOList
    ) {
        BasketResponseDTO dto = basketService.addOrUpdateBasketItems(basketId, basketItemRequestDTOList);
        return ResponseEntity.ok().body(dto);
    }

    @DeleteMapping(value = "/{basketId}")
    public ResponseEntity<Void> deleteBasket(@PathVariable Long basketId) {
        basketService.deleteBasket(basketId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/{basketId}/items")
    public ResponseEntity<BasketResponseDTO> deleteBasketItems(
            @PathVariable Long basketId,
            @RequestBody @Valid List<String> productsId
    ) {
        BasketResponseDTO basketResponseDTO = basketService.deleteBasketItems(basketId, productsId);
        return ResponseEntity.ok().body(basketResponseDTO);
    }

    @PutMapping(value = "/{basketId}/reopen")
    public ResponseEntity<Void> reopenBasket(@PathVariable Long basketId) {
        basketService.reopenBasket(basketId);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/{basketId}/checkout")
    public ResponseEntity<CheckoutBasketModelResponseDTO> performCheckout(@PathVariable Long basketId) {
        CheckoutBasketModelResponseDTO checkoutBasketModelResponseDTO = basketService.performCheckout(basketId);
        return ResponseEntity.ok().body(checkoutBasketModelResponseDTO);
    }
}
