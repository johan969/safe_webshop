package se.iths.johan.safe_webshop.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CookieController {
    @GetMapping("/")
    public String cookie(HttpServletRequest request, Model model) {
        model.addAttribute("hasConsent", hasConsent(request));
        return "index";
    }

    @PostMapping("/consent")
    public String consent(HttpServletResponse response) {
        ResponseCookie consentCookie = ResponseCookie.from("cookie_consent", "true")
                .httpOnly(true)
                .secure(false)
                .path("/")
                .sameSite("Lax")
                .maxAge(60 * 60 * 24 * 30)
                .build();
        response.addHeader("Set-Cookie", consentCookie.toString());
        return "redirect:/";
    }

    private boolean hasConsent(HttpServletRequest request) {
        String consent = getCookieValue(request, "cookie_consent");
        if (consent != null && consent.equals("true")) {
            return true;
        }
        return false;
    }

    private String getCookieValue(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }

        for (Cookie cookie : cookies) {
            if (cookieName.equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return null;
    }
}
