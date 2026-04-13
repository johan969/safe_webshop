package se.iths.johan.safe_webshop.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import se.iths.johan.safe_webshop.model.Cart;
import se.iths.johan.safe_webshop.model.Product;
import se.iths.johan.safe_webshop.repository.ProductRepository;

import java.util.HashMap;
import java.util.Map;

@Controller
@SessionAttributes("cart")
public class CartController {

    @ModelAttribute("cart")
    public Cart createCart() {
        return new Cart();
    }

    @Autowired
    private ProductRepository productRepository;

    @PostMapping("/cart/add/{id}")
    public String addToCart(@PathVariable Long id, HttpSession httpSession) {

        Cart cart = (Cart) httpSession.getAttribute("cart");

        if (cart == null) {
            cart = new Cart();
        }
        cart.addProduct(id);
        httpSession.setAttribute("cart", cart);

        return "redirect:/products";
    }

    @PostMapping("/cart/remove/{id}")
    public String removeFromCart(@PathVariable Long id,
                                 @ModelAttribute("cart") Cart cart) {
        cart.removeProduct(id);
        return "redirect:/cart";
    }

    @GetMapping("/cart")
    public String viewCart(@ModelAttribute("cart") Cart cart,
                           Model model) {
        Map<Product, Integer> cartDetails = new HashMap<>();
        double total = 0;

        for (Map.Entry<Long, Integer> entry : cart.getItems().entrySet()) {
            Product product = productRepository.findById(entry.getKey()).orElse(null);

            if (product != null) {
                int quantity = entry.getValue();
                cartDetails.put(product, quantity);

                total += product.getPrice() * quantity;
            }
        }
        model.addAttribute("CartItems", cartDetails);
        model.addAttribute("totalPrice", total);

        return "cart";
    }

}
