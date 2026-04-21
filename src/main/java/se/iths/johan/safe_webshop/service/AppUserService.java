package se.iths.johan.safe_webshop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import se.iths.johan.safe_webshop.model.AppUser;
import se.iths.johan.safe_webshop.repository.AppUserRepository;
import se.iths.johan.safe_webshop.validation.AppUserValidate;
import se.iths.johan.springmessenger.model.Email;
import se.iths.johan.springmessenger.model.Message;
import se.iths.johan.springmessenger.service.MessageService;

@Service
public class AppUserService {
    private final AppUserRepository appUserRepository;
    private final MessageService messageService;
    private final AppUserValidate validate;
    private final PasswordEncoder passwordEncoder;

    public AppUserService(AppUserRepository appUserRepository, MessageService messageService, AppUserValidate validate, PasswordEncoder passwordEncoder) {
        this.appUserRepository = appUserRepository;
        this.messageService = messageService;
        this.validate = validate;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDetails loadUserByUsername(String username) {
        AppUser appUser = appUserRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));

        return User.builder()
                .username(appUser.getUsername())
                .password(appUser.getPassword())
                .roles(appUser.getRole())
                .build();


    }

    public void sendUserDataByEmail(String username) {
        AppUser user = findByUsername(username);

        Email email = new Email();
        email.setSubject("Användar information");
        email.setRecipient(user.getUsername());
        email.setMessage("Din information" +
                "email: " + user.getUsername() + "\n" +
                "Rol: " + user.getRole() + "\n" +
                "Consent: " + user.getConsent());
        messageService.send(email);

    }

    public void updateConsent(String username, boolean consent) {
        AppUser user = findByUsername(username);
        user.setConsent(consent);
        appUserRepository.save(user);
    }

    public AppUser findByUsername(String username) {
        return appUserRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
    }

    public void deleteUser(String username) {
        appUserRepository.delete(findByUsername(username));
    }

    public AppUser createUser(String username, String password, boolean consent) {
        validate.validateNewUser(username, password, consent);
        AppUser user = new AppUser();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole("USER");
        user.setConsent(consent);
        return appUserRepository.save(user);
    }


}
