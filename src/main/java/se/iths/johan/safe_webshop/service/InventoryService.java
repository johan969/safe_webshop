package se.iths.johan.safe_webshop.service;

import org.jspecify.annotations.Nullable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import se.iths.johan.safe_webshop.model.Product;
import se.iths.johan.safe_webshop.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
public class InventoryService {
    private final ProductRepository productRepository;

    public InventoryService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product saveItem(String name, double price, String category, int stock, String imageUrl) {

        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        product.setCategory(category);
        product.setStock(stock);
        product.setImageUrl(imageUrl);
        return productRepository.save(product);
    }

    public Product updateItem(Long id, Product updatedItem) {
        Product existingItem = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("item not found with id: " + id));
        existingItem.setId(updatedItem.getId());
        existingItem.setName(updatedItem.getName());
        existingItem.setPrice(updatedItem.getPrice());
        existingItem.setStock(updatedItem.getStock());
        existingItem.setImageUrl(updatedItem.getImageUrl());
        return productRepository.save(existingItem);
    }

    public List<Product> productList() {
        return productRepository.findAll();
    }

    public Product findItem(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id " + id));
    }

    public Product deleteItem(Long id) {
        productRepository.deleteById(id);
        return null;
    }


    public List<Product> getAllItems() {
        return productRepository.findAll();
    }
}
