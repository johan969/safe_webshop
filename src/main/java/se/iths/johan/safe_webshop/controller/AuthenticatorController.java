package se.iths.johan.safe_webshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthenticatorController {

    @GetMapping("/ott/sent")
    public String OttSent(){
        return "ott-sent";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/ott/trigger")
    public String triggerOtt(){
        return "redirect:/ott/generate";
    }



}
