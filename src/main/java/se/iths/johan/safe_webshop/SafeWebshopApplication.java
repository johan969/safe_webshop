package se.iths.johan.safe_webshop;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import se.iths.johan.safe_webshop.model.AppUser;
import se.iths.johan.safe_webshop.repository.AppUserRepository;

@SpringBootApplication(scanBasePackages = {
        "se.iths.johan.safe_webshop",
        "se.iths.johan"
})
public class SafeWebshopApplication {

    public static void main(String[] args) {
        SpringApplication.run(SafeWebshopApplication.class, args);
    }

    @Bean
    CommandLineRunner inti(AppUserRepository appUserRepository, PasswordEncoder passwordEncoder) {
        return args -> {

            //För inloggning på gmail så är lösenordet Safe_password123

         if (appUserRepository.findByUsername("safe.webshop.iths@gmail.com").isEmpty()){
             AppUser admin = new AppUser();
             admin.setUsername("safe.webshop.iths@gmail.com");
             admin.setPassword(passwordEncoder.encode("password"));
             admin.setRole("ADMIN");
             admin.setConsent(true);
             appUserRepository.save(admin);


         }


        };
    }

}
