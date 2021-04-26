package com.projekt.kasyno;

import com.projekt.kasyno.user.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
public class KasynoApplication {

	public static void main(String[] args) {
		SpringApplication.run(KasynoApplication.class, args);
	}
}
