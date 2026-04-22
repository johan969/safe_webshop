package se.iths.johan.safe_webshop.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import se.iths.johan.safe_webshop.model.AppUser;
import se.iths.johan.safe_webshop.service.AppUserService;

@Controller
@RequestMapping("/profile")
public class ProfileController {


    private final AppUserService appUserService;

    public ProfileController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @GetMapping
    public String profile(Model model, Authentication auth) {

        AppUser user = appUserService.findByUsername(auth.getName());
        model.addAttribute("user", user);
        return "profile";


    }

    @PostMapping("/send-data")
    public String sendData(RedirectAttributes ra, Authentication auth) {
        try{
            appUserService.sendUserDataByEmail(auth.getName());
            ra.addFlashAttribute("message", "User info has been  sent to: "+ auth.getName());
        }catch(Exception e){
            ra.addFlashAttribute("message", "oops! something went wrong");
        }
        return "redirect:/profile";

    }


    @PostMapping("/delete")
    public String deleteProfile(Authentication auth) {
        appUserService.deleteUser(auth.getName());
        return "redirect:/profile";

    }

}
