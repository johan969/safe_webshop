package se.iths.johan.safe_webshop.validation;

import org.springframework.stereotype.Component;
import se.iths.johan.safe_webshop.repository.AppUserRepository;

@Component
public class AppUserValidate {
    private final AppUserRepository appUserRepository;

    public AppUserValidate(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    public boolean validateNewUser(String username, String password, boolean consent) {
        boolean validated = false;
        if (username == null || username.isBlank() || password == null || password.isBlank()) {
            throw new NullPointerException("Username or password can't be empty");
        }
        if (!consent) {
            throw new RuntimeException("You have not given consent to our Terms of Service");
        } else {
            validated = true;
        }
        return validated;
    }
}