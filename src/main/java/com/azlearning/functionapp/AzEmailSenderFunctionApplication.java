package com.azlearning.functionapp;

import com.azlearning.functionapp.function.EmailSender;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(exclude = {org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration.class})
public class AzEmailSenderFunctionApplication {

	public static void main(String[] args) {
		SpringApplication.run(AzEmailSenderFunctionApplication.class, args);
	}

}
