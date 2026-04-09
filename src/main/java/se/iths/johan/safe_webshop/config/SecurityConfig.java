package se.iths.johan.safe_webshop.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final OttSuccessHandler ottSuccessHandler;

    public SecurityConfig(OttSuccessHandler ottSuccessHandler) {
        this.ottSuccessHandler = ottSuccessHandler;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/login", "/register", "/ott/sent", "/login/ott").permitAll()
                        .anyRequest().authenticated()
                )
                .oneTimeTokenLogin(ott-> ott
                        .tokenGenerationSuccessHandler(
                                ottSuccessHandler)

                )
                .formLogin(form ->form
                        .defaultSuccessUrl("/")
                        .permitAll()
                ).logout(logout -> logout
                        .logoutSuccessUrl("/")
                        .permitAll()
                );

        return http.build();

    }


}
