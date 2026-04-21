package se.iths.johan.safe_webshop.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.iths.johan.safe_webshop.model.AppUser;
import se.iths.johan.safe_webshop.model.Cart;
import se.iths.johan.safe_webshop.model.Order;
import se.iths.johan.safe_webshop.model.Product;
import se.iths.johan.safe_webshop.repository.AppUserRepository;
import se.iths.johan.safe_webshop.repository.OrderRepository;
import se.iths.johan.safe_webshop.repository.ProductRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MockOrderServiceTest {

    @Mock
    private EmailService emailService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private AppUserRepository appUserRepository;

    @InjectMocks
    private OrderService orderService;

    @Test
    public void getOrderShouldReturnOrderAndSendConfirmation() {

        String username = "testuser";

        AppUser appuser = new AppUser();
        appuser.setUsername(username);

        when(appUserRepository.findByUsername(username))
                .thenReturn(Optional.of(appuser));

        Product product1 = new Product();
        product1.setName("MSI Gaming Laptop");
        product1.setPrice(10000);
        product1.setStock(10);

        Product product2 = new Product();
        product2.setName("Wireless Mouse");
        product2.setPrice(200);
        product2.setStock(10);

        when(productRepository.findById(1L))
                .thenReturn(Optional.of(product1));

        when(productRepository.findById(2L))
                .thenReturn(Optional.of(product2));

        when(orderRepository.save(any(Order.class)))
                .thenAnswer(invocation -> invocation.getArgument(0)); //

        Cart cart = new Cart();
        cart.getItems().put(1L, 2);
        cart.getItems().put(2L, 4);

        Order order = orderService.getOrder(cart, username);
        System.out.println("Order: " + order);

        assertNotNull(order);
        assertEquals(20800, order.getTotalPrice(), 0.001);
        assertEquals(2, order.getItems().size());

        verify(orderRepository).save(order);
        verify(emailService).sendOrderConfirmation(order);

    }

    @Test
    void cartShouldBeClearedAfterOrder() {

        String username = "testuser";

        AppUser appuser = new AppUser();
        appuser.setUsername(username);

        when(appUserRepository.findByUsername(username))
                .thenReturn(Optional.of(appuser));

        Product product = new Product();
        product.setName("MSI Gaming Laptop");
        product.setPrice(10000);
        product.setStock(10);

        when(productRepository.findById(1L))
                .thenReturn(Optional.of(product));

        Cart cart = new Cart();
        cart.getItems().put(1L, 2);

        orderService.getOrder(cart, username);

        assertTrue(cart.getItems().isEmpty());

    }

    @Test
    void shouldThrowIfOutOfStock() {

        String username = "testuser";

        AppUser appuser = new AppUser();
        appuser.setUsername(username);

        when(appUserRepository.findByUsername(username))
                .thenReturn(Optional.of(appuser));

        Product product = new Product();
        product.setName("MSI Gaming Laptop");
        product.setPrice(10000);
        product.setStock(1);

        when(productRepository.findById(1L))
                .thenReturn(Optional.of(product));

        Cart cart = new Cart();
        cart.getItems().put(1L, 2);

        assertThrows(RuntimeException.class, () ->
                orderService.getOrder(cart, username));
    }
}