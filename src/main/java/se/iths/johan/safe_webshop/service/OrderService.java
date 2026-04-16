package se.iths.johan.safe_webshop.service;

import org.springframework.stereotype.Service;
import se.iths.johan.safe_webshop.model.*;
import se.iths.johan.safe_webshop.repository.AppUserRepository;
import se.iths.johan.safe_webshop.repository.OrderRepository;
import se.iths.johan.safe_webshop.repository.ProductRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class OrderService {

    private EmailService emailService;

    private ProductRepository productRepository;

    private OrderRepository orderRepository;

    private AppUserRepository appUserRepository;

    public OrderService(EmailService emailService, ProductRepository productRepository, OrderRepository orderRepository, AppUserRepository appUserRepository) {
        this.emailService = emailService;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.appUserRepository = appUserRepository;
    }

    public Order getOrder(Cart cart, String username) {
        AppUser appUser = appUserRepository
                .findByUsername(username)
                .orElse(null);

        if (appUser == null) {
            return null;
        }

        Order order = new Order();
        order.setUsername(username);
        order.setOrderDate(LocalDateTime.now());

        List<OrderItem> orderItems = new ArrayList<>();
        double total = 0;

        for (Map.Entry<Long, Integer> entry : cart.getItems().entrySet()) {

            Product product = productRepository.findById(entry.getKey()).orElse(null);

            if (product != null) {

                int quantity = entry.getValue();

                OrderItem item = new OrderItem();
                item.setProductName(product.getName());
                item.setQuantity(quantity);
                item.setPrice(product.getPrice());
                item.setOrder(order);

                orderItems.add(item);

                total += product.getPrice() * quantity;

            }
        }

        order.setItems(orderItems);
        order.setTotalPrice(total);

        orderRepository.save(order);
        emailService.sendOrderConfirmation(order);

        cart.clear();
        return order;
    }

}
