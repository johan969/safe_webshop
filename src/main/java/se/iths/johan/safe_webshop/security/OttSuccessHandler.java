package se.iths.johan.safe_webshop.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.ott.OneTimeToken;
import org.springframework.security.web.authentication.ott.OneTimeTokenGenerationSuccessHandler;
import org.springframework.security.web.authentication.ott.RedirectOneTimeTokenGenerationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import se.iths.johan.springmessenger.model.Email;
import se.iths.johan.springmessenger.service.MessageService;

import java.io.IOException;

@Component
public class OttSuccessHandler implements OneTimeTokenGenerationSuccessHandler {

    private final MessageService messageService;

    private final RedirectOneTimeTokenGenerationSuccessHandler redirectOneTimeTokenGenerationSuccessHandler;

    public OttSuccessHandler(RedirectOneTimeTokenGenerationSuccessHandler redirectOneTimeTokenGenerationSuccessHandler, MessageService messageService) {
        this.redirectOneTimeTokenGenerationSuccessHandler = redirectOneTimeTokenGenerationSuccessHandler;
        this.messageService = messageService;
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, OneTimeToken oneTimeToken) throws IOException {



        String link = ServletUriComponentsBuilder.fromContextPath(request)
                .path("/login/ott")
                .queryParam("token", oneTimeToken.getTokenValue())
                .toUriString();

        Email email = new Email();
        email.setRecipient(oneTimeToken.getUsername());
        email.setMessage(link);
        messageService.send(email);



        //Ska ersättas med en email service

        System.out.println("========================================");
        System.out.println("NY INLOGGNINGSLÄNK:");
        System.out.println(link);
        System.out.println("========================================");

        this.redirectOneTimeTokenGenerationSuccessHandler.handle(request, response, oneTimeToken);
    }



}
