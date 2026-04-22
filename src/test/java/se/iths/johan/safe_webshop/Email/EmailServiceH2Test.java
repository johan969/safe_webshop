package se.iths.johan.safe_webshop.Email;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import se.iths.johan.safe_webshop.model.Order;
import se.iths.johan.safe_webshop.model.OrderItem;
import se.iths.johan.safe_webshop.service.EmailService;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
@Transactional
public class EmailServiceH2Test {

    @Autowired
    private EmailService emailService;

    @Test
    public void shouldGenerateOrderConfirmation_whenValidOrderIsProvided() {

        Order order = new Order();
        order.setUsername("test123@testuser.se");
        order.setOrderDate(LocalDateTime.now());
        order.setTotalPrice(21000.0);

        OrderItem orderItem = new OrderItem();
        orderItem.setProductName("TV");
        orderItem.setQuantity(1);
        orderItem.setPrice(21000.0);

        order.setItems(List.of(orderItem));

        emailService.sendOrderConfirmation(order);
    }

}
