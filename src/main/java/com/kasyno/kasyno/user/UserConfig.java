package com.kasyno.kasyno.user;

import com.kasyno.kasyno.Oauth2.AuthenticationProvider;
import com.kasyno.kasyno.security.ApplicationUserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static com.kasyno.kasyno.security.ApplicationUserRole.*;

@Configuration
public class UserConfig {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }


    @Bean
    CommandLineRunner commandLineRunner(UserRepository repository){
        return args -> {
            User stefan = new User(
                    "Stefan",
                    "stefan@gmail.com",
                    passwordEncoder.encode("password"),
                    USER,
                    AuthenticationProvider.LOCAL,
                    LocalDate.of(1998, Month.JANUARY, 5),
                    LocalDate.now(),
                    (long)1000,
                    true

            );

            User marian = new User(
                    "Marian",
                    "marian@gmail.com",
                    passwordEncoder.encode("password"),
                    ADMIN,
                    AuthenticationProvider.LOCAL,
                    LocalDate.of(1997, Month.JANUARY, 5),
                    LocalDate.now(),
                    (long)1000,
                    true
            );

            repository.saveAll(
                    List.of(stefan, marian)
            );
        };
    }
}
