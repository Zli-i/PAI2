package com.kasyno.kasyno;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableAsync(proxyTargetClass=true)
@EnableCaching(proxyTargetClass = true)
public class KasynoApplication {

	public static void main(String[] args) {
		SpringApplication.run(KasynoApplication.class, args);
	}

}
