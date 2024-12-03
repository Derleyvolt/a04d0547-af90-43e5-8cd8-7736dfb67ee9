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
@RequestMapping("/api")
@Validated
public class BasketController {
    @Autowired
    private BasketService basketService;

    @GetMapping(value = "/{tenantName}/baskets")
    public ResponseEntity<List<BasketResponseDTO>> getAllBaskets(@PathVariable String tenantName) {
        List<BasketResponseDTO> baskets = basketService.getAllBasket(tenantName);

        return ResponseEntity.ok(baskets);
    }

    @PostMapping(value = "/{tenantName}/baskets")
    public ResponseEntity<BasketResponseDTO> createBasket(
            @PathVariable String tenantName,
            @RequestBody @Valid List<BasketItemRequestDTO> basketProducts
    ) {
        BasketResponseDTO basket = basketService.createBasket(tenantName, basketProducts);

        URI location = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(basket.getId())
            .toUri();

        return ResponseEntity.created(location).body(basket);
    }

    @GetMapping(value = "/{tenantName}/baskets/{basketId}")
    public ResponseEntity<BasketResponseDTO> detailBasket(@PathVariable String tenantName, @PathVariable Long basketId) {
        BasketResponseDTO basketModel = basketService.detailBasket(basketId, tenantName);
        return ResponseEntity.ok().body(basketModel);
    }

    @PutMapping(value = "/{tenantName}/baskets/{basketId}/items/increase-quantity")
    public ResponseEntity<BasketResponseDTO> increaseItemQuantity(
            @PathVariable String tenantName,
            @PathVariable Long basketId,
            @RequestBody @Valid List<BasketItemRequestDTO> basketItemRequestDTOList
    ) {
        BasketResponseDTO basketResponseDTO = basketService.increaseItemsQuantity(basketId, tenantName, basketItemRequestDTOList);
        return ResponseEntity.ok().body(basketResponseDTO);
    }

    @PutMapping(value = "/{tenantName}/baskets/{basketId}/items/decrease-quantity")
    public ResponseEntity<BasketResponseDTO> decreaseItemQuantity(
            @PathVariable String tenantName,
            @PathVariable Long basketId,
            @RequestBody @Valid List<BasketItemRequestDTO> basketItemRequestDTOList
    ) {
        BasketResponseDTO basketResponseDTO = basketService.decreaseItemsQuantity(basketId, tenantName, basketItemRequestDTOList);
        return ResponseEntity.ok().body(basketResponseDTO);
    }

    @PutMapping(value = "/{tenantName}/baskets/{basketId}/items/add-or-update")
    public ResponseEntity<BasketResponseDTO> addOrUpdateItems(
            @PathVariable String tenantName,
            @PathVariable Long basketId,
            @RequestBody @Valid List<BasketItemRequestDTO> basketItemRequestDTOList
    ) {
        BasketResponseDTO dto = basketService.addOrUpdateBasketItems(basketId, tenantName, basketItemRequestDTOList);
        return ResponseEntity.ok().body(dto);
    }

    @DeleteMapping(value = "/{tenantName}/baskets/{basketId}")
    public ResponseEntity<Void> deleteBasket(@PathVariable String tenantName, @PathVariable Long basketId) {
        basketService.deleteBasket(basketId, tenantName);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/{tenantName}/baskets/{basketId}/items")
    public ResponseEntity<BasketResponseDTO> deleteBasketItems(
            @PathVariable String tenantName,
            @PathVariable Long basketId,
            @RequestBody @Valid List<String> productsId
    ) {
        BasketResponseDTO basketResponseDTO = basketService.deleteBasketItems(basketId, tenantName, productsId);
        return ResponseEntity.ok().body(basketResponseDTO);
    }

    @PutMapping(value = "/{tenantName}/baskets/{basketId}/reopen")
    public ResponseEntity<Void> reopenBasket(@PathVariable String tenantName, @PathVariable Long basketId) {
        basketService.reopenBasket(basketId, tenantName);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/{tenantName}/baskets/{basketId}/checkout")
    public ResponseEntity<CheckoutBasketModelResponseDTO> performCheckout(@PathVariable String tenantName, @PathVariable Long basketId) {
        CheckoutBasketModelResponseDTO checkoutBasketModelResponseDTO = basketService.performCheckout(basketId, tenantName);
        return ResponseEntity.ok().body(checkoutBasketModelResponseDTO);
    }
}
