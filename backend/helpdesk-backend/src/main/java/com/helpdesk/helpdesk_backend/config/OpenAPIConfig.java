package com.helpdesk.helpdesk_backend.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.List;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.List;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI myOpenAPI() {
        Server devServer = new Server();
        devServer.setUrl("http://localhost:8080");
        devServer.setDescription("Development Server");

        Contact contact = new Contact();
        contact.setEmail("helpdesk@company.com");
        contact.setName("Helpdesk Support");

        Info info = new Info()
                .title("Helpdesk Ticketing System API")
                .version("1.0.0")
                .contact(contact)
                .description("""
                        This API exposes endpoints for the Helpdesk Ticketing System.
                        
                        ## Features:
                        - User Management
                        - Ticket Lifecycle Management
                        - Dashboard Statistics
                        - Real-time Notifications
                        
                        ## Available Roles:
                        - ADMIN: Full system access
                        - TECHNICIAN: Can view and assign tickets
                        - USER: Can create and view own tickets
                        """);

        return new OpenAPI()
                .info(info)
                .servers(List.of(devServer));
    }
}