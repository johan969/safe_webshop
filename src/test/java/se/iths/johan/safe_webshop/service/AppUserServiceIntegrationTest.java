package se.iths.johan.safe_webshop.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import se.iths.johan.safe_webshop.model.AppUser;
import se.iths.johan.safe_webshop.repository.AppUserRepository;
import se.iths.johan.springmessenger.service.MessageService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest

public class AppUserServiceIntegrationTest {

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private AppUserRepository appUserRepository;

    @MockitoBean
    private MessageService messageService;

    private AppUser appUser;

    @BeforeEach
    public void setUp() {
        appUserRepository.deleteAll();
        appUser = new AppUser();
        appUser.setUsername("Test@test.se");
        appUser.setPassword("password");
        appUser.setConsent(true);
        appUser.setRole("USER");

        appUserRepository.save(appUser);

    }

    @Test
    public void loadUserByUsernameTest() {


        UserDetails userDetails = appUserService.loadUserByUsername("Test@test.se");

        assertEquals("password", userDetails.getPassword());


    }

    @Test
    public void sendUserDataByEmailTest() {

        appUserService.sendUserDataByEmail("Test@test.se");

        verify(messageService, times(1)).send(any());


    }

    @Test
    public void findUserByUsernameTest() {

        AppUser user = appUserService.findByUsername("Test@test.se");
        assertEquals("password", user.getPassword());

    }

    @Test
    public void deleteUserByUsernameTest() {
        appUserService.deleteUser("Test@test.se");

        assertTrue(appUserRepository.findByUsername("Test@test.se").isEmpty());
    }

    @Test
    public void createUserTest() {
        AppUser user = appUserService.createUser("test", "123", true);
        AppUser foundUser = appUserService.findByUsername("test");

        assertEquals("test", foundUser.getUsername());
        assertEquals("USER", foundUser.getRole());
        assertTrue(foundUser.getConsent());

    }
}

