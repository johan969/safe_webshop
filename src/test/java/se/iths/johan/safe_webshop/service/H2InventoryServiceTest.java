package se.iths.johan.safe_webshop.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import se.iths.johan.safe_webshop.model.Product;
import se.iths.johan.safe_webshop.repository.ProductRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class H2InventoryServiceTest {

    @Autowired
    private InventoryService inventoryService;
    @Autowired
    private ProductRepository productRepository;


    @BeforeEach
    void delete() {

    }

    @Test
    void saveItem() {
        productRepository.deleteAll();
        Product savedItem = inventoryService.saveItem("mac", 1000,
                "laptop", 10, "url");
        assertEquals("mac", savedItem.getName());
        assertEquals(1000, savedItem.getPrice());
        assertEquals("laptop", savedItem.getCategory());
        assertEquals(10, savedItem.getStock());
        assertEquals("url", savedItem.getImageUrl());
    }

    @Test
    void updateItem() {
        productRepository.deleteAll();
        Product item = inventoryService.saveItem("mac", 1000,
                "laptop", 10, "url");

        Long savedId = item.getId();
        Product existingItem = productRepository.findById(savedId)
                .orElseThrow(() -> new RuntimeException("item not found"));

        existingItem.setName("hp");
        existingItem.setPrice(99);
        existingItem.setCategory("pc");
        existingItem.setStock(9);
        existingItem.setImageUrl("image");

        Product updatedItem = inventoryService.updateItem(savedId, existingItem);


        assertEquals("hp", updatedItem.getName());
        assertEquals(99, updatedItem.getPrice());
        assertEquals("pc", updatedItem.getCategory());
        assertEquals(9, updatedItem.getStock());
        assertEquals("image", updatedItem.getImageUrl());
    }


    @Test
    void productList() {
        productRepository.deleteAll();
        productRepository.deleteAll();

        List<Product> emptyProductList = inventoryService.productList();
        Assertions.assertThat(emptyProductList).isEmpty();

        inventoryService.saveItem("mac", 1000, "laptop", 10, "url-1");
        inventoryService.saveItem("hp", 900, "pc", 8, "url-2");

        List<Product> productList = inventoryService.productList();
        Assertions.assertThat(productList).hasSize(2);

    }

    @Test
    void findItem() {
        productRepository.deleteAll();
        Product item = inventoryService.saveItem("mac", 100, "pc", 2, "image");

        Product listedItem = productRepository.findAll().get(0);

        assertEquals("mac", listedItem.getName());
        assertEquals(100, listedItem.getPrice());
        assertEquals("pc", listedItem.getCategory());
        assertEquals(2, listedItem.getStock());
        assertEquals("image", listedItem.getImageUrl());
    }

    @Test
    void deleteItem() {
        productRepository.deleteAll();
        inventoryService.saveItem("mac", 1000, "laptop", 10, "url-1");
        inventoryService.saveItem("hp", 900, "pc", 8, "url-2");

        List<Product> productList = inventoryService.productList();
        Assertions.assertThat(productList).hasSize(2);

        productRepository.deleteAll();

        List<Product> emptyProductList = inventoryService.productList();
        Assertions.assertThat(emptyProductList).isEmpty();

    }

}