package se.iths.johan.safe_webshop.Email;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import se.iths.johan.safe_webshop.model.Order;
import se.iths.johan.safe_webshop.model.OrderItem;
import se.iths.johan.safe_webshop.service.EmailService;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
@Transactional
public class EmailServiceH2Test {

    @Test
    public void shouldGenerateOrderConfirmation_whenValidOrderIsProvided() {
        EmailService emailService = new EmailService();

        Order order = new Order();
        order.setUsername("testuser@test.se");
        order.setOrderDate(LocalDateTime.now());
        order.setTotalPrice(18000.0);

        OrderItem orderItem = new OrderItem();
        orderItem.setProductName("Laptop");
        orderItem.setQuantity(1);
        orderItem.setPrice(18000.0);

        order.setItems(List.of(orderItem));

        emailService.sendOrderConfirmation(order);
    }

}
