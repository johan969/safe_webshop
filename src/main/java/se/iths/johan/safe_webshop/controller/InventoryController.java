package se.iths.johan.safe_webshop.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import se.iths.johan.safe_webshop.model.Product;
import se.iths.johan.safe_webshop.service.InventoryService;

@Controller
@RequestMapping("/inventory/admin")
@PreAuthorize("hasRole('ADMIN')")
public class InventoryController {
    private final InventoryService inventoryService;


    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping
    public String homePage(Model model) {
        model.addAttribute("items", inventoryService.productList());
        return "inventory-admin/home-page";
    }

    @GetMapping("/{id}")
    public String inventoryDetail(Model model, @PathVariable Long id) {
        model.addAttribute("detail", inventoryService.findItem(id));
        return "inventory-admin/inventory-detail";
    }


    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("item", new Product());
        return "inventory-admin/new-item";
    }

    @PostMapping("/new")
    public String saveItem(Model model, String name, double price, String category,
                           int stock, String imageUrl) {
        model.addAttribute("item", inventoryService.saveItem(name, price, category, stock, imageUrl));
        return "redirect:/inventory/admin";
    }

    @PostMapping("/{id}/delete")
    public String deleteItem(Model model, @PathVariable Long id) {
        model.addAttribute("delete", inventoryService.deleteItem(id));
        return "redirect:/inventory/admin";
    }

    @GetMapping("/{id}/edit")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        Product product = inventoryService.findItem(id);
        model.addAttribute("update", product);
        return "inventory-admin/update-item";
    }

    @PostMapping("/{id}")
    public String updateLiaSearch(@PathVariable Long id, @ModelAttribute Product product) {
        inventoryService.updateItem(id, product);
        return "redirect:/inventory/admin";
    }
}
