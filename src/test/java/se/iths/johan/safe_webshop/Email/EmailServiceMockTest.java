package se.iths.johan.safe_webshop.Email;

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
import se.iths.johan.safe_webshop.service.EmailService;
import se.iths.johan.safe_webshop.service.OrderService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmailServiceMockTest {
    @Mock
    private EmailService emailService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private AppUserRepository appUserRepository;

    @InjectMocks
    private OrderService orderService;

    @Test
    public void shouldSendOrderConfirmationEmail_whenOrderIsCreated() {
        String username = "test123@test12.se";

        AppUser appUser = new AppUser();
        appUser.setUsername(username);

        when(appUserRepository.findByUsername(username))
                .thenReturn(Optional.of(appUser));

        Product product = new Product();
        product.setId(1L);
        product.setName("Laptop");
        product.setPrice(17000.0);
        product.setStock(10);

        when(productRepository.findById(1L))
                .thenReturn(Optional.of(product));

        when(orderRepository.save(any(Order.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Cart cart = new Cart();
        cart.addProduct(1L, 1);


        Order result = orderService.getOrder(cart, username);

        verify(emailService, times(1))
                .sendOrderConfirmation(any(Order.class));

        assertEquals(1, result.getItems().size());
        assertEquals(17000.0, result.getTotalPrice(), 0.01);


    }

}
