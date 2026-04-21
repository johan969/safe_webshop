package se.iths.johan.safe_webshop.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.iths.johan.safe_webshop.model.Product;
import se.iths.johan.safe_webshop.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MockitoInventoryServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private InventoryService inventoryService;

    @BeforeEach
    void setup() {
        productRepository.deleteAll();
    }

    @Test
    void saveItem() {
        Long id = 1L;
        String name = "mac";
        double price = 1000;
        String category = "laptop";
        int stock = 10;
        String imageUrl = "url";

        Product savedProduct = new Product(id, name, price, category, stock, imageUrl);

        when(productRepository.save(any(Product.class))).thenReturn(savedProduct);

        Product result = inventoryService.saveItem(name, price, category, stock, imageUrl);

        assertEquals(id, result.getId());
        assertEquals(name, result.getName());
        assertEquals(price, result.getPrice());
        assertEquals(category, result.getCategory());
        assertEquals(stock, result.getStock());
        assertEquals(imageUrl, result.getImageUrl());

        verify(productRepository).save(any(Product.class));
    }

    @Test
    void productList() {
        Product item1 = new Product(1L, "phone", 1000, "electronics", 10, "image1");
        Product item2 = new Product(2L, "velo freeze", 49, "snus", 10, "image2");

        when(productRepository.findAll()).thenReturn(List.of(item1, item2));

        List<Product> result = inventoryService.getAllItems();

        assertEquals(2, result.size());
        verify(productRepository).findAll();
    }

    @Test
    void findItem() {
        String name = "mac";
        double price = 1000;
        String category = "laptop";
        int stock = 10;
        String imageUrl = "url";
        Product item = new Product(1L, name, price, category, stock, imageUrl);
        Long savedId = item.getId();
        when(productRepository.findById(savedId)).thenReturn(Optional.of(item));
        Product result = inventoryService.findItem(savedId);

        assertEquals(name, result.getName());
        assertEquals(price, result.getPrice());
        assertEquals(category, result.getCategory());
        assertEquals(stock, result.getStock());
        assertEquals(imageUrl, result.getImageUrl());

        verify(productRepository).findById(1L);
    }

    @Test
    void deleteItem() {
        Product item = inventoryService.saveItem("phone", 1000, "electronics", 10, "image1");
        List<Product> productList = inventoryService.getAllItems();
        inventoryService.deleteItem(1L);
        assertEquals(0, productList.size());
        verify(productRepository).deleteById(1L);
    }
}