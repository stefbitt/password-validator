package com.itau.password_validator;

import com.itau.password_validator.config.ApiProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(ApiProperties.class)
public class PasswordValidatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(PasswordValidatorApplication.class, args);
	}

}
