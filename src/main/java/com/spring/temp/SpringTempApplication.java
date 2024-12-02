package com.spring.temp;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;


@EnableJpaAuditing
@EnableAsync
@SpringBootApplication
@EnableAspectJAutoProxy
public class SpringTempApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringTempApplication.class, args);
	}

	@Bean
	public CommandLineRunner runner(){
		return  args -> {
			// You can use this area for automatic insert operations after the create-drop phase or your initial actions before the app is running.
			System.out.println("Ok");
		};
	}

}
