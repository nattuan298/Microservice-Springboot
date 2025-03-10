package com.springboot.accounts;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@OpenAPIDefinition(
    info = @Info(
        title = "Accounts microservice REST API",
        description = "API Documentation",
        version = "v1",
        contact = @Contact(
            name = "Tyler Nguyen",
            email = "natuan.298@gmail.com"
        ),
        license = @License(
            name = "Apache 2.0"
        )
    ),
    externalDocs = @ExternalDocumentation(
        description = "External Description"
    )
)
public class AccountsApplication {

  public static void main(String[] args) {
    SpringApplication.run(AccountsApplication.class, args);
  }

}
