package se.iths.johan.safe_webshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import se.iths.johan.safe_webshop.model.Cart;
import se.iths.johan.safe_webshop.model.Order;
import se.iths.johan.safe_webshop.model.OrderItem;
import se.iths.johan.safe_webshop.model.Product;
import se.iths.johan.safe_webshop.repository.AppUserRepository;
import se.iths.johan.safe_webshop.repository.OrderRepository;
import se.iths.johan.safe_webshop.repository.ProductRepository;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class OrderController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private AppUserRepository appUserRepository;

    @PostMapping("/checkout")
    public String checkout(@ModelAttribute("cart") Cart cart,
                           Principal principal) { //Principal = inloggad användare

        if (principal == null) {
            return "redirect:/login";
        }


        Order order = new Order();

        List<OrderItem> orderItems = new ArrayList<>();
        double total = 0;

        for (Map.Entry<Long, Integer> entry : cart.getItems().entrySet()) {

            Product product = productRepository.findById(entry.getKey()).orElse(null);

            if (product != null) {

                int quantity = entry.getValue();

                OrderItem item = new OrderItem();
                item.setProduct(product);
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

        cart.clear();
        ;

        return "redirect:/order-confirmation";
    }

    @GetMapping("/order-confirmation")
    public String confirmation() {
        return "order-confirmation";
    }
}
