package se.iths.johan.safe_webshop.Email;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.iths.johan.safe_webshop.model.Order;
import se.iths.johan.safe_webshop.model.OrderItem;
import se.iths.johan.safe_webshop.service.EmailService;
import se.iths.johan.springmessenger.model.Email;
import se.iths.johan.springmessenger.service.MessageService;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmailServiceMockTest {
    @Mock
    private MessageService messageService;

    @InjectMocks
    private EmailService emailService;

    @Test
    public void shouldSendEmailWithCorrectContent_WhenOrderIsCreated() {

        String username = "testuser@test.se";

        Order order = new Order();
        order.setId(1L);
        order.setUsername(username);
        order.setOrderDate((LocalDateTime.of(2026, 4, 22, 14, 30)));
        order.setTotalPrice(17000.0);

        OrderItem orderItem = new OrderItem();
        orderItem.setProductName("Laptop");
        orderItem.setQuantity(1);
        orderItem.setPrice(17000.0);

        order.setItems(List.of(orderItem));

        emailService.sendOrderConfirmation(order);

        verify(messageService, times(1))
                .send(any(Email.class));

        assertEquals(username, order.getUsername());
        assertEquals(1, order.getItems().size());
        
        assertEquals("Laptop", order.getItems().get(0).getProductName());
        assertEquals(1, order.getItems().get(0).getQuantity());
        assertEquals(17000.0, order.getTotalPrice());

    }


}
