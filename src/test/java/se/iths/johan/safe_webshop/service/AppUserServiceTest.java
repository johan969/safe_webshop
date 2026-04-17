package se.iths.johan.safe_webshop.service;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import se.iths.johan.springmessenger.messaging.Messenger;
import se.iths.johan.springmessenger.service.MessageService;

import java.util.Map;

@Configuration
public class AppUserServiceTest {


    @Bean
    @Primary
    public MessageService fakeMessageService() {

        Messenger messenger = message -> System.out.println(message);
        return new MessageService(Map.of("email", messenger));

    }
}
