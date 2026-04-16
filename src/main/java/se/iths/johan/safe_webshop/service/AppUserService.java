package se.iths.johan.safe_webshop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import se.iths.johan.safe_webshop.model.AppUser;
import se.iths.johan.safe_webshop.repository.AppUserRepository;
import se.iths.johan.springmessenger.model.Email;
import se.iths.johan.springmessenger.model.Message;
import se.iths.johan.springmessenger.service.MessageService;

@Service
public class AppUserService {


    private AppUserRepository appUserRepository;
    private MessageService messageService;

    public AppUserService(AppUserRepository appUserRepository, MessageService messageService) {
        this.appUserRepository = appUserRepository;
        this.messageService = messageService;
    }

    public UserDetails loadUserByUsername(String username){
        AppUser appUser = appUserRepository.findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("Username not found"));

        return User.builder()
                .username(appUser.getUsername())
                .password(appUser.getPassword())
                .roles(appUser.getRole())
                .build();


    }

    public void sendUserDataByEmail(String username){
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

    public AppUser findByUsername(String username){
        return appUserRepository.findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("Username not found"));
    }

    public void deleteUser(String username){
        appUserRepository.delete(findByUsername(username));
    }


}
