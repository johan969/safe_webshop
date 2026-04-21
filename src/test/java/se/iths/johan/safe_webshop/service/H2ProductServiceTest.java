package se.iths.johan.safe_webshop.service;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import se.iths.johan.safe_webshop.model.Product;
import se.iths.johan.safe_webshop.repository.ProductRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class H2ProductServiceTest {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductService productService;

    @Test
    void getAllProductsShouldReturnProducts() {

        Product product = new Product();
        product.setName("MSI Laptop");
        product.setPrice(10000);

        productService.save(product);
        List<Product> products = productService.getAllProducts();

        assertEquals(1, products.size());
        assertEquals("MSI Laptop", products.get(0).getName());
    }

}