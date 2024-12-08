package com.challenge.checkout.service;

import com.challenge.checkout.dto.request.BasketItemRequestDTO;
import com.challenge.checkout.dto.response.BasketResponseDTO;
import com.challenge.checkout.entity.product.ProductBase;
import com.challenge.checkout.enums.BasketStatusEnum;
import com.challenge.checkout.exception.BadRequestException;
import com.challenge.checkout.exception.basket.BasketAlreadyOpenException;
import com.challenge.checkout.exception.basket.BasketNotFoundException;
import com.challenge.checkout.exception.product.ProductBadRequestException;
import com.challenge.checkout.exception.product.ProductNotFoundException;
import com.challenge.checkout.mapper.BasketItemMapper;
import com.challenge.checkout.mapper.BasketMapper;
import com.challenge.checkout.model.BasketItemModel;
import com.challenge.checkout.model.BasketModel;
import com.challenge.checkout.model.TenantModel;
import com.challenge.checkout.repository.BasketRepository;
import com.challenge.checkout.repository.TenantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class BasketServiceTest {
    @Mock
    private BasketRepository basketRepository;

    @Mock
    private TenantRepository tenantRepository;

    @Mock
    private ProductService productService;

    @Mock
    private BasketMapper basketMapper;

    @Mock
    private BasketItemMapper basketItemMapper;

    @Autowired
    @InjectMocks
    private BasketService basketService;

    private BasketModel basket;
    private TenantModel tenant;

    @BeforeEach
    void setup() {
        List<ProductBase> productBaseList = new ArrayList<>(Arrays.asList(
                new ProductBase("JKDkdja415", "Product 1", BigInteger.valueOf(400)),
                new ProductBase("Dkfhajdg2b", "Product 2", BigInteger.valueOf(300)),
                new ProductBase("G155dawGGa", "Product 3", BigInteger.valueOf(200))
        ));

        basket = new BasketModel();
        tenant = new TenantModel();

        tenant.setName("some_tenant");

        Mockito.when(tenantRepository.findByName(tenant.getName())).thenReturn(Optional.of(tenant));
        Mockito.when(basketRepository.save(basket)).thenReturn(basket);
        Mockito.when(basketRepository.findByIdAndTenant(1L, tenant)).thenReturn(Optional.of(basket));
        Mockito.when(productService.getProducts(tenant.getName())).thenReturn(productBaseList);
    }

    private List<BasketItemModel> getMockBasketItemsModel() {
        return new ArrayList<>(List.of(
                new BasketItemModel(1L, "JKDkdja415", "Product 1", 2, null),
                new BasketItemModel(2L, "Dkfhajdg2b", "Product 2", 1, null)
        ));
    }

    @Test
    void testGetBasketSuccessfully() {
        BasketModel result = basketService.getBasketOrException(1L, "some_tenant");

        assertNotNull(result);
        assertEquals(result, basket);
    }

    @Test
    void testGetBasketWhenTenantDoesNotExist() {
        String tenantName = "tenant_that_does_not_exist";
        Mockito.when(tenantRepository.findByName(tenantName)).thenReturn(Optional.empty());

        Exception exception = assertThrows(
                BadRequestException.class,
                () -> basketService.getBasketOrException(1L, tenantName)
        );

        assertEquals(String.format("Tenant %s not found.", tenantName), exception.getMessage());
    }

    @Test
    void testGetBasketWhenBasketDoesNotExist() {
        Mockito.when(basketRepository.findByIdAndTenant(1L, tenant)).thenReturn(Optional.empty());

        Exception exception = assertThrows(
                BasketNotFoundException.class,
                () -> basketService.getBasketOrException(1L, "some_tenant")
        );

        assertEquals("Basket Not found", exception.getMessage());
    }

    @Test
    void testGetAllBasket() {
        Mockito.when(basketMapper.toResponseDTOList(Mockito.anyList())).thenReturn(
                List.of(new BasketResponseDTO())
        );

        Mockito.when(basketRepository.findByTenant(tenant)).thenReturn(List.of(basket));
        List<BasketResponseDTO> basketResponseList = basketService.getAllBasket("some_tenant");

        assertEquals(1, basketResponseList.size());
    }

    @Test
    void testIncreaseItemsQuantitySuccessfully() {
        List<BasketItemModel> itemsModel = getMockBasketItemsModel();

        Mockito.when(basketItemMapper.toModelList(Mockito.any())).thenReturn(getMockBasketItemsModel());

        // Only basketModel quantity matter, not output
        Mockito.when(basketMapper.toResponseDTO(Mockito.any())).thenReturn(Mockito.any());

        basket.setItems(itemsModel);
        basketService.increaseItemsQuantity(1L, "some_tenant", new ArrayList<>());

        assertEquals(4, basket.getItems().get(0).getQuantity());
        assertEquals(2, basket.getItems().get(1).getQuantity());
    }

    @Test
    void testIncreaseItemsQuantityThatDoNotExist() {
        Mockito.when(basketItemMapper.toModelList(Mockito.any())).thenReturn(getMockBasketItemsModel());

        // Only basketModel quantity matter, not output
        Mockito.when(basketMapper.toResponseDTO(Mockito.any())).thenReturn(Mockito.any());

        // basket.setItems(itemsModel);
        // Basket has no items

        assertThrows(
                ProductNotFoundException.class,
                () -> basketService.increaseItemsQuantity(
                        1L, "some_tenant", new ArrayList<>()
                )
        );
    }

    @Test
    void testDecreaseItemsQuantitySuccessfully() {
        List<BasketItemModel> itemsModel = getMockBasketItemsModel();

        Mockito.when(basketItemMapper.toModelList(Mockito.any())).thenReturn(getMockBasketItemsModel());

        // Only basketModel quantity matter, not output
        Mockito.when(basketMapper.toResponseDTO(Mockito.any())).thenReturn(Mockito.any());

        itemsModel.get(0).setQuantity(10);
        itemsModel.get(1).setQuantity(10);

        basket.setItems(itemsModel);
        basketService.decreaseItemsQuantity(1L, "some_tenant", new ArrayList<>());

        assertEquals(8, basket.getItems().get(0).getQuantity());
        assertEquals(9, basket.getItems().get(1).getQuantity());
    }

    @Test
    void testDecreaseItemsQuantityToNonPositiveValue() {
        List<BasketItemModel> itemsModel = getMockBasketItemsModel();

        Mockito.when(basketItemMapper.toModelList(Mockito.any())).thenReturn(getMockBasketItemsModel());

        // Only basketModel quantity matter, not output
        Mockito.when(basketMapper.toResponseDTO(Mockito.any())).thenReturn(Mockito.any());

        basket.setItems(itemsModel);

        Exception exception = assertThrows(
                BadRequestException.class,
                () -> basketService.decreaseItemsQuantity(
                        1L, "some_tenant", new ArrayList<>()
                )
        );

        assertEquals(
                String.format("Product %s need has positive quantity", itemsModel.get(0).getProductId()),
                exception.getMessage()
        );
    }

    @Test
    void testUpdateItemsQuantitySuccessfully() {
        List<BasketItemModel> itemsModel = getMockBasketItemsModel();

        Mockito.when(basketItemMapper.toModelList(Mockito.any())).thenReturn(getMockBasketItemsModel());

        // Only basketModel quantity matter, not output
        Mockito.when(basketMapper.toResponseDTO(Mockito.any())).thenReturn(Mockito.any());

        itemsModel.get(0).setQuantity(10);
        itemsModel.get(1).setQuantity(100);
        basket.setItems(itemsModel);
        basketService.addOrUpdateBasketItems(1L, "some_tenant", new ArrayList<>());

        assertEquals(2, itemsModel.get(0).getQuantity());
        assertEquals(1, itemsModel.get(1).getQuantity());
    }

    @Test
    void testAddItemsSuccessfully() {
        List<BasketItemModel> itemsModel = getMockBasketItemsModel();

        Mockito.when(basketItemMapper.toModelList(Mockito.any()))
                .thenReturn(
                        List.of(
                                new BasketItemModel(null, "G155dawGGa", "" ,2, null)
                        ));

        // Only basketModel quantity matter, not output
        Mockito.when(basketMapper.toResponseDTO(Mockito.any())).thenReturn(Mockito.any());

        basket.setItems(itemsModel);

        basketService.addOrUpdateBasketItems(1L, "some_tenant", new ArrayList<>());

        assertEquals(3, basket.getItems().size());
    }

    @Test
    void testAddOrUpdateItemThatDoNotExist() {
        List<BasketItemModel> itemsModel = getMockBasketItemsModel();

        basket.setItems(itemsModel);

        assertThrows(
                ProductBadRequestException.class,
                () -> basketService.addOrUpdateBasketItems(
                        1L, "some_tenant", List.of(new BasketItemRequestDTO("0xBEEF", 1))
                )
        );
    }

    @Test
    void testDeleteBasketItemsSuccessfully() {
        List<BasketItemModel> itemsModel = getMockBasketItemsModel();

        // Only basketModel quantity matter, not output
        Mockito.when(basketMapper.toResponseDTO(Mockito.any())).thenReturn(Mockito.any());

        basket.setItems(itemsModel);

        List<String> itemsToDelete = new ArrayList<>(List.of("JKDkdja415", "Dkfhajdg2b"));

        basketService.deleteBasketItems(1L, "some_tenant", itemsToDelete);

        assertEquals(0, basket.getItems().size());
    }

    @Test
    void testDeleteBasketItemsThatDoNotExists() {
        List<BasketItemModel> itemsModel = getMockBasketItemsModel();

        // Only basketModel quantity matter, not output
        Mockito.when(basketMapper.toResponseDTO(Mockito.any())).thenReturn(Mockito.any());

        basket.setItems(itemsModel);

        assertThrows(
                ProductBadRequestException.class,
                () -> basketService.deleteBasketItems(
                        1L, "some_tenant", List.of("0xBEEF")
                )
        );
    }

    @Test
    void testReopenBasket() {
        basket.setStatus(BasketStatusEnum.CHECKOUT);
        basketService.reopenBasket(1L, "some_tenant");
        assertEquals(BasketStatusEnum.ACTIVE, basket.getStatus());
    }

    @Test
    void testReopenBasketWhenBasketAlreadyIsActive() {
        basket.setStatus(BasketStatusEnum.ACTIVE);

        assertThrows(
                BasketAlreadyOpenException.class,
                () -> basketService.reopenBasket(1L, "some_tenant")
        );
    }
}