package com.kasyno.kasyno.user;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Configuration
public class UserConfig {

    @Bean
    CommandLineRunner commandLineRunner(UserRepository repository){
        return args -> {
            User stefan = new User(
                    "Stefan",
                    "costam@gmail.com",
                    LocalDate.of(1998, Month.JANUARY, 5),
                    LocalDate.of(2021, Month.JANUARY, 5)

            );

            User marian = new User(
                    "marian",
                    "costam@gmail.com",
                    LocalDate.of(1997, Month.JANUARY, 5),
                    LocalDate.of(2021, Month.JANUARY, 5)

            );

            repository.saveAll(
                    List.of(stefan, marian)
            );
        };
    }
}
