package se.iths.johan.safe_webshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import se.iths.johan.safe_webshop.model.Cart;
import se.iths.johan.safe_webshop.model.Order;
import se.iths.johan.safe_webshop.service.OrderService;

import java.security.Principal;

@Controller
public class OrderController {

    private OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/checkout")
    public String checkout(@ModelAttribute("cart") Cart cart,
                           Principal principal, Model model) { //Principal = inloggad användare

        String username = principal.getName();

        Order order = orderService.getOrder(cart, username);
        if (order == null) return "redirect:/login";

        model.addAttribute("order", order);
        return "order-confirmation";
    }


    @GetMapping("/order-confirmation")
    public String confirmation() {
        return "order-confirmation";
    }
}
