package se.iths.johan.safe_webshop.service;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import se.iths.johan.safe_webshop.model.AppUser;
import se.iths.johan.safe_webshop.repository.AppUserRepository;
import se.iths.johan.safe_webshop.validation.AppUserValidate;
import se.iths.johan.springmessenger.model.Email;
import se.iths.johan.springmessenger.service.MessageService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AppUserServiceTest {

    @Mock
    private AppUserRepository appUserRepository;

    @Mock
    private MessageService messageService;

    @Mock
    private AppUserValidate appUserValidate;

    @InjectMocks
    private AppUserService appUserService;

    private AppUser user;


    @BeforeEach
    public void setUp() {
        user = new AppUser();
        user.setUsername("test@test.se");
        user.setPassword("test123");
        user.setConsent(true);
        user.setRole("USER");
    }

    @Test
    public void sendUserDataByEmailTest() {

        //Arrange

        when(appUserRepository.findByUsername("test@test.se")).thenReturn(Optional.of(user));
        ArgumentCaptor<Email> emailArgumentCaptor = ArgumentCaptor.forClass(Email.class);

        //Act
        appUserService.sendUserDataByEmail("test@test.se");

        //Assert
        verify(messageService).send(emailArgumentCaptor.capture());

        Email email = emailArgumentCaptor.getValue();
        assertEquals(email.getSubject(), "Användar information");

    }

    @Test
    public void findByUsernameTest () {


        when(appUserRepository.findByUsername("test@test.se")).thenReturn(Optional.of(user));


        AppUser result = appUserService.findByUsername("test@test.se");

        assertNotNull(result);
        assertEquals(result.getPassword(), "test123");
    }

    @Test
    public void deleteUserTest(){

        when(appUserRepository.findByUsername("test@test.se")).thenReturn(Optional.of(user));

        appUserService.deleteUser("test@test.se");

        verify(appUserRepository, times(1)).delete(user);
        verify(appUserRepository, times(1)).findByUsername("test@test.se");



    }
}
