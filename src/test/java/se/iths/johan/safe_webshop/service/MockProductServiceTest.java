package se.iths.johan.safe_webshop.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.iths.johan.safe_webshop.model.Product;
import se.iths.johan.safe_webshop.repository.ProductRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class MockProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void getAllProductsShouldReturnProducts() {

        Product p1 = new Product();
        p1.setName("Product 1");

        Product p2 = new Product();
        p2.setName("Product 2");

        List<Product> mockProducts = List.of(p1, p2);

        when(productRepository.findAll()).thenReturn(mockProducts);

        List<Product> result = productService.getAllProducts();

        assertEquals(2, result.size());

    }

    @Test
    void saveProductShouldSave() {

        Product product = new Product();
        product.setName("New product");

        productService.save(product);

        verify(productRepository, times(1)).save(product);
    }
}