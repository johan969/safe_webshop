package se.iths.johan.safe_webshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import se.iths.johan.safe_webshop.model.AppUser;
import se.iths.johan.safe_webshop.service.AppUserService;

@Controller
@RequestMapping("")
public class AppUserController {
    private final AppUserService service;

    public AppUserController(AppUserService service) {
        this.service = service;
    }

    @GetMapping("/register")
    public String registerUserForm(Model model) {
        model.addAttribute("register", new AppUser());
        return "appuser/user-register-page";
    }

    @PostMapping("/register")
    public String registerUser(Model model, String username, String password) {
        model.addAttribute("register", service.createUser(username, password));
        return "redirect:/";
    }

}
