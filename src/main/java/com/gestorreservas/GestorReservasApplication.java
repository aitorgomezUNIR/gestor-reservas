package com.gestorreservas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
@AutoConfigurationPackage
@ComponentScan
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true)
public class GestorReservasApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestorReservasApplication.class, args);
    }

}
