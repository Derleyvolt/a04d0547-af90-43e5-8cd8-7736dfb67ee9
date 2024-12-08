package com.challenge.checkout.service;

import com.challenge.checkout.dto.request.BasketItemRequestDTO;
import com.challenge.checkout.dto.response.BasketResponseDTO;
import com.challenge.checkout.dto.response.CheckoutBasketModelResponseDTO;
import com.challenge.checkout.entity.product.ProductBase;
import com.challenge.checkout.entity.product.ProductWithPromotions;
import com.challenge.checkout.entity.promotion.Promotion;
import com.challenge.checkout.entity.promotion.PromotionResult;
import com.challenge.checkout.enums.BasketStatusEnum;
import com.challenge.checkout.exception.BadRequestException;
import com.challenge.checkout.exception.basket.BasketAlreadyOpenException;
import com.challenge.checkout.exception.basket.BasketForbiddenException;
import com.challenge.checkout.exception.basket.BasketNotFoundException;
import com.challenge.checkout.exception.product.ProductBadRequestException;
import com.challenge.checkout.mapper.BasketItemMapper;
import com.challenge.checkout.mapper.BasketMapper;
import com.challenge.checkout.mapper.CheckoutBasketModelMapper;
import com.challenge.checkout.model.*;
import com.challenge.checkout.repository.BasketRepository;
import com.challenge.checkout.repository.TenantRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toMap;

@Service
public class BasketService {
    private final BasketRepository basketRepository;
    private final ProductService productService;
    private final BasketMapper basketMapper;
    private final BasketItemMapper basketItemMapper;
    private final CheckoutBasketModelMapper checkoutBasketModelMapper;
    private final TenantRepository tenantRepository;

    public BasketService(
        BasketRepository basketRepository,
        ProductService productService,
        BasketMapper basketMapper,
        BasketItemMapper basketItemMapper,
        CheckoutBasketModelMapper checkoutBasketModelMapper,
        TenantRepository tenantRepository
    ) {
        this.basketRepository = basketRepository;
        this.productService = productService;
        this.basketMapper = basketMapper;
        this.basketItemMapper = basketItemMapper;
        this.checkoutBasketModelMapper = checkoutBasketModelMapper;
        this.tenantRepository = tenantRepository;
    }

    private List<BasketItemRequestDTO> groupingProducts(List<BasketItemRequestDTO> basketItemsDTO) {
        Map<String, BasketItemRequestDTO> productMap = new HashMap<>();

        for(BasketItemRequestDTO basketProduct : basketItemsDTO) {
            productMap.computeIfAbsent(basketProduct.getProductId(), k -> new BasketItemRequestDTO(
                basketProduct.getProductId(),
                0
            )).increaseQuantity(basketProduct.getQuantity());
        }

        return List.copyOf(productMap.values());
    }

    public TenantModel getTenantOrException(String tenantName) {
        return tenantRepository.findByName(tenantName).orElseThrow(
            () -> new BadRequestException(String.format("Tenant %s not found.", tenantName))
        );
    }

    // Throw exception if you cannot modify Basket, or it does not exist
    private BasketModel getBasketToModifyOrException(Long basketId, String tenantName) {
        TenantModel tenant = getTenantOrException(tenantName);
        BasketModel basketModel = basketRepository.findByIdAndTenant(basketId, tenant).
                orElseThrow(BasketNotFoundException::new);

        if (basketModel.getStatus() == BasketStatusEnum.CHECKOUT) {
            throw new BasketForbiddenException("The basket is already checked out.");
        }

        return basketModel;
    };

    public BasketModel getBasketOrException(Long basketId, String tenantName) {
        TenantModel tenant = getTenantOrException(tenantName);
        return basketRepository.findByIdAndTenant(basketId, tenant).
                orElseThrow(BasketNotFoundException::new);
    }

    private void validateProductExistence(String tenantName, List<BasketItemRequestDTO> itemsRequestDTO) {
        TenantModel tenant = getTenantOrException(tenantName);
        List<ProductBase> products = productService.getProducts(tenantName);

        Set<String> productsId = products.stream()
                .map(ProductBase::getId)
                .collect(Collectors.toSet());

        // Check if all products in the basket are present in the market
        List<String> productsNotFound = itemsRequestDTO
            .stream()
            .map(BasketItemRequestDTO::getProductId)
            .filter(productId -> !productsId.contains(productId)).toList();

        // Throw an exception if any products are not found in the market
        if (!productsNotFound.isEmpty()) {
            List<String> errors = new ArrayList<>();

            productsNotFound.forEach(productId -> {
                errors.add("The product with ID " + productId + " does not exist.");
            });

            throw new ProductBadRequestException(errors);
        }
    }

    @Transactional
    public BasketResponseDTO deleteBasketItems(Long basketId, String tenantName, List<String> productsId) {
        BasketModel basketModel = getBasketToModifyOrException(basketId, tenantName);

        List<String> errors = new ArrayList<>();

        Set<String> storedProductsId = basketModel.getItems().stream()
                                    .map(BasketItemModel::getProductId)
                                    .collect(Collectors.toSet());

        // Get all prodcuts that do not exist in the basket
        productsId.stream().filter(productId -> !storedProductsId.contains(productId)).forEach(productId -> {
            errors.add("The product with ID " + productId + " does not exist in Basket.");
        });

        // If some product do not exist in the basket, so throw exception
        if (!errors.isEmpty()) throw new ProductBadRequestException(errors);

        basketModel.getItems().removeIf(item -> productsId.contains(item.getProductId()));

        return basketMapper.toResponseDTO(basketRepository.save(basketModel));
    }

    public List<BasketResponseDTO> getAllBasket(String tenantName) {
        TenantModel tenant = getTenantOrException(tenantName);
        List<BasketModel> baskets = basketRepository.findByTenant(tenant);
        return basketMapper.toResponseDTOList(baskets);
    }

    public BasketResponseDTO increaseItemsQuantity(Long basketId, String tenantName, List<BasketItemRequestDTO> itemsRequestDTO) {
        validateProductExistence(tenantName, itemsRequestDTO);
        BasketModel basketModel = getBasketToModifyOrException(basketId, tenantName);
        List<BasketItemModel> basketItems = basketItemMapper.toModelList(itemsRequestDTO);

        basketModel.increaseProductQuantity(basketItems);
        return basketMapper.toResponseDTO(basketRepository.save(basketModel));
    }

    public BasketResponseDTO decreaseItemsQuantity(Long basketId, String tenantName, List<BasketItemRequestDTO> itemsRequestDTO) {
        validateProductExistence(tenantName, itemsRequestDTO);
        BasketModel basketModel = getBasketToModifyOrException(basketId, tenantName);
        List<BasketItemModel> basketItems = basketItemMapper.toModelList(itemsRequestDTO);

        basketModel.decreaseProductQuantity(basketItems);
        return basketMapper.toResponseDTO(basketRepository.save(basketModel));
    }

    public BasketResponseDTO addOrUpdateBasketItems(Long basketId, String tenantName, List<BasketItemRequestDTO> itemsRequestDTO) {
        validateProductExistence(tenantName, itemsRequestDTO);
        BasketModel basketModel = getBasketToModifyOrException(basketId, tenantName);
        basketModel.addOrUpdate(
                basketItemMapper.toModelList(itemsRequestDTO),
                productService.getProducts(tenantName)
        );
        return basketMapper.toResponseDTO(basketRepository.save(basketModel));
    }

    public BasketResponseDTO createBasket(String tenantName, List<BasketItemRequestDTO> itemsRequestDTO) {
        TenantModel tenant = getTenantOrException(tenantName);
        validateProductExistence(tenantName, itemsRequestDTO);

        List<BasketItemModel> basketItems = basketItemMapper.toModelList(groupingProducts(itemsRequestDTO));

        BasketModel newBasketModel = new BasketModel();

        newBasketModel.setTenant(tenant);
        newBasketModel.setStatus(BasketStatusEnum.ACTIVE);
        newBasketModel.addOrUpdate(basketItems, productService.getProducts(tenantName));
        return basketMapper.toResponseDTO(basketRepository.save(newBasketModel));
    }

    @Transactional
    public void reopenBasket(Long basketId, String tenantName) {
        TenantModel tenant = getTenantOrException(tenantName);
        BasketModel basketModel = basketRepository.findByIdAndTenant(basketId, tenant)
                                .orElseThrow(BasketNotFoundException::new);

        if (basketModel.getStatus() == BasketStatusEnum.ACTIVE) {
            throw new BasketAlreadyOpenException();
        }


        basketModel.setCheckoutBasket(null);

        basketModel.setStatus(BasketStatusEnum.ACTIVE);
        basketRepository.save(basketModel);
    }

    @Transactional
    public void deleteBasket(Long basketId, String tenantName) {
        TenantModel tenant = getTenantOrException(tenantName);
        BasketModel basketModel = basketRepository.findByIdAndTenant(basketId, tenant).orElseThrow(BasketNotFoundException::new);
        basketRepository.delete(basketModel);
    }

    public BasketResponseDTO detailBasket(Long basketId, String tenantName) {
        TenantModel tenant = getTenantOrException(tenantName);
        BasketModel basketModel = basketRepository.findByIdAndTenant(basketId, tenant).orElseThrow(BasketNotFoundException::new);
        return basketMapper.toResponseDTO(basketModel);
    }

    @Transactional
    public CheckoutBasketModelResponseDTO performCheckout(Long basketId, String tenantName) {
        BasketModel basketModel = getBasketToModifyOrException(basketId, tenantName);

        if (basketModel.getItems().isEmpty()) {
            throw new BadRequestException("Its not possible make checkout with no items");
        }

        basketModel.setStatus(BasketStatusEnum.CHECKOUT);

        List<BasketItemModel> basketItems = basketModel.getItems();

        Map<String, PromotionResult> discountByProduct = new HashMap<>();

        basketItems.forEach(basketItem -> {
            // Make request to wiremock API to get product promotions
            ProductWithPromotions product = productService.getProductWithPromotions(tenantName, basketItem.getProductId());
            List<Promotion> promotions = new ArrayList<>(product.getPromotions());

            // Order Basket by priority
            Collections.sort(promotions);

            PromotionResult promotionResult = PromotionResult.builder()
                    .subTotal(
                            new BigDecimal(
                                    product.getPrice().multiply(BigInteger.valueOf(basketItem.getQuantity()))
                            )
                    )
                    .quantity(basketItem.getQuantity())
                    .price(product.getPrice())
                    .discount(BigDecimal.valueOf(0))
                    .build();

            for(Promotion promotion : promotions) {
                PromotionResult partialResult = promotion.calculateDiscount(
                        promotionResult.getSubTotal(),
                        promotionResult.getPrice(),
                        promotionResult.getQuantity()
                );

                promotionResult.aggregate(partialResult);
            }

            promotionResult.setProductName(product.getName());

            discountByProduct.put(product.getId(), promotionResult);
        });

        CheckoutBasketModel checkoutModel = new CheckoutBasketModel();

        List<CheckoutBasketItemModel> checkoutItems = new ArrayList<>();

        for(Map.Entry<String, PromotionResult> entry : discountByProduct.entrySet()) {
            PromotionResult promotionResult = entry.getValue();

            checkoutItems.add(
                    CheckoutBasketItemModel.builder()
                            .total(
                                    promotionResult.getPrice().intValue() * promotionResult.getQuantity()
                            )
                            .productId(entry.getKey())
                            .productName(promotionResult.getProductName())
                            .totalDiscounts(promotionResult.getDiscount())
                            .totalPayable(promotionResult.getSubTotal())
                            .quantity(promotionResult.getQuantity())
                            .unitPrice(promotionResult.getPrice())
                            .checkoutBasket(checkoutModel)
                            .build()
            );
        }

        checkoutItems.forEach(checkoutItem -> {
            checkoutModel.setTotal(checkoutModel.getTotal() + checkoutItem.getTotal());
            checkoutModel.setTotalPayable(checkoutModel.getTotalPayable().add(checkoutItem.getTotalPayable()));
            checkoutModel.setTotalDiscounts(checkoutModel.getTotalDiscounts().add(checkoutItem.getTotalDiscounts()));
        });

        checkoutModel.setItems(checkoutItems);
        basketModel.setCheckoutBasket(checkoutModel);
        basketRepository.save(basketModel);

        return checkoutBasketModelMapper.toResponseDTO(checkoutModel);
    }

    // Show stored checkout
    public CheckoutBasketModelResponseDTO showCheckout(Long basketId, String tenantName) {
        BasketModel basketModel = getBasketOrException(basketId, tenantName);

        if (basketModel.getStatus() == BasketStatusEnum.ACTIVE) {
            throw new BadRequestException("Basket is not in checkout status");
        }

        return checkoutBasketModelMapper.toResponseDTO(basketModel.getCheckoutBasket());
    }
}
