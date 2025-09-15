package com.wallace.spring.boot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SwaggerConfig {

	@Bean
	OpenAPI customOpenAPI() {
		return new OpenAPI()
				.info(new Info().title("Digital Bank API").version("1.0").description("API com segurança JWT"))
				.addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
				.components(new io.swagger.v3.oas.models.Components().addSecuritySchemes("bearerAuth",
						new SecurityScheme().name("bearerAuth").type(SecurityScheme.Type.HTTP).scheme("bearer")
								.bearerFormat("JWT")));
	}
}
