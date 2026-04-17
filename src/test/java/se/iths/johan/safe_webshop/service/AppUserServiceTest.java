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


    @BeforeEach
    public void setUp() {

    }

    @Test
    public void sendUserDataByEmailTest() {



        AppUser user = new AppUser();
        user.setUsername("test@test.se");
        user.setPassword("test123");
        user.setConsent(true);
        user.setRole("USER");

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
}
