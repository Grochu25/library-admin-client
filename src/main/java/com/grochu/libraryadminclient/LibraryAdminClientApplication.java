package com.grochu.libraryadminclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
public class LibraryAdminClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(LibraryAdminClientApplication.class, args);
    }

}
