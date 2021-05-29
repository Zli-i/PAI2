package com.kasyno.kasyno.user;

import com.kasyno.kasyno.auth.AuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

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
                    "costam@gmail.com",
                    passwordEncoder.encode("password"),
                    "USER",
                    AuthenticationProvider.LOCAL,
                    LocalDate.of(1998, Month.JANUARY, 5),
                    LocalDate.of(2021, Month.JANUARY, 5)

            );

            User marian = new User(
                    "Marian",
                    "costam@gmail.com",
                    passwordEncoder.encode("password"),
                    "ADMIN",
                    AuthenticationProvider.LOCAL,
                    LocalDate.of(1997, Month.JANUARY, 5),
                    LocalDate.of(2021, Month.JANUARY, 5)

            );

            repository.saveAll(
                    List.of(stefan, marian)
            );
        };
    }
}
