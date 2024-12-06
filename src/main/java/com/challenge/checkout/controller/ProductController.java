package com.challenge.checkout.controller;

import com.challenge.checkout.entity.product.ProductBase;
import com.challenge.checkout.entity.product.ProductWithPromotions;
import com.challenge.checkout.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/markets")
@Validated
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping(value = "/{tenantName}/products")
    public ResponseEntity<List<ProductBase>> getAllBaskets(@PathVariable String tenantName) {
        List<ProductBase> products = productService.getProducts(tenantName);
        return ResponseEntity.ok(products);
    }

    @GetMapping(value = "/{tenantName}/products/{productId}")
    public ResponseEntity<ProductWithPromotions> getAllBaskets(
            @PathVariable String tenantName,
            @PathVariable String productId
    ) {
        ProductWithPromotions product = productService.getProductWithPromotions(tenantName, productId);
        return ResponseEntity.ok(product);
    }
}
