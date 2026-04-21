package se.iths.johan.safe_webshop.service;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import se.iths.johan.safe_webshop.model.AppUser;
import se.iths.johan.safe_webshop.model.Cart;
import se.iths.johan.safe_webshop.model.Order;
import se.iths.johan.safe_webshop.model.Product;
import se.iths.johan.safe_webshop.repository.AppUserRepository;
import se.iths.johan.safe_webshop.repository.OrderRepository;
import se.iths.johan.safe_webshop.repository.ProductRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class H2OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private OrderRepository orderRepository;

    @MockitoBean
    private EmailService emailService;
    @Autowired
    private AppUserRepository appUserRepository;

    @Test
    void getOrderShouldReturnOrder() {

        AppUser appUser = new AppUser();
        appUser.setUsername("testuser");
        appUserRepository.save(appUser);

        Product product = new Product();
        product.setName("MSI Laptop");
        product.setPrice(10000);
        product.setStock(10);
        productRepository.save(product);

        Cart cart = new Cart();
        cart.getItems().put(product.getId(), 2);

        Order order = orderService.getOrder(cart, "testuser");

        assertNotNull(order);
        assertEquals(20000, order.getTotalPrice());

        List<Order> orders = orderRepository.findAll();
        assertEquals(1, orders.size());

        verify(emailService).sendOrderConfirmation(order);
    }

}