package smartLegalApi.presentation.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Configuração do Swagger/OpenAPI para documentação da API
 * 
 * Acesse em: http://localhost:8080/swagger-ui.html
 * API Docs JSON: http://localhost:8080/v3/api-docs
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(apiInfo())
            .servers(apiServers())
            .addSecurityItem(securityRequirement())
            .schemaRequirement("Bearer Authentication", securityScheme());
    }

    private Info apiInfo() {
        return new Info()
            .title("Smart Legal API")
            .description("""
                API REST para gerenciamento de escritório de advocacia.
                
                **Funcionalidades:**
                - Gerenciamento de Advogados
                - Gerenciamento de Clientes
                - Gerenciamento de Processos Judiciais
                - Gerenciamento de Andamentos Processuais
                - Gerenciamento de Documentos
                - Geração de Petições com IA (Google Gemini)
                - Autenticação JWT
                - Sistema de Auditoria
                
                **Tecnologias:**
                - Spring Boot 3.5.6
                - Java 17
                - MySQL
                - JPA/Hibernate
                - Spring Security + JWT
                - Google Gemini AI
                """)
            .version("1.0.0")
            .contact(apiContact())
            .license(apiLicense());
    }

    private Contact apiContact() {
        return new Contact()
            .name("Time Smart Legal")
            .email("contato@smartlegal.com.br")
            .url("https://smartlegal.com.br");
    }

    private License apiLicense() {
        return new License()
            .name("Apache 2.0")
            .url("https://www.apache.org/licenses/LICENSE-2.0.html");
    }

    private List<Server> apiServers() {
        return List.of(
            new Server()
                .url("http://localhost:8080")
                .description("Servidor de Desenvolvimento"),
            new Server()
                .url("https://api.smartlegal.com.br")
                .description("Servidor de Produção")
        );
    }

    private SecurityRequirement securityRequirement() {
        return new SecurityRequirement()
            .addList("Bearer Authentication");
    }

    private SecurityScheme securityScheme() {
        return new SecurityScheme()
            .name("Bearer Authentication")
            .type(SecurityScheme.Type.HTTP)
            .scheme("bearer")
            .bearerFormat("JWT")
            .description("Insira o token JWT obtido no endpoint /api/auth/login");
    }
}

