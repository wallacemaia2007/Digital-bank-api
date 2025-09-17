package com.wallace.spring.boot.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.wallace.spring.boot.config.security.JwtAuthenticationEntryPoint;
import com.wallace.spring.boot.config.security.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

	private final JwtAuthenticationFilter jwtAuthenticationFilter;
	private final AuthenticationProvider authenticationProvider;
	private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	private final CustomAccessDeniedHandler customAccessDeniedHandler;

	public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter,
			AuthenticationProvider authenticationProvider,
			JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
			CustomAccessDeniedHandler customAccessDeniedHandler) {
		this.jwtAuthenticationFilter = jwtAuthenticationFilter;
		this.authenticationProvider = authenticationProvider;
		this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
		this.customAccessDeniedHandler = customAccessDeniedHandler;
	}

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable())
				.cors(cors -> cors.configurationSource(corsConfigurationSource()))
				.authorizeHttpRequests(auth -> auth
						.requestMatchers("/api/v1/auth/**").permitAll()
						.requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
						.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
						
						.requestMatchers(HttpMethod.GET, "/clientes", "/clientes/**").hasAnyAuthority("user:read", "admin:read")
						.requestMatchers(HttpMethod.POST, "/clientes").hasAnyAuthority("user:write", "admin:write")
						.requestMatchers(HttpMethod.PUT, "/clientes/**").hasAuthority("admin:update") 
						.requestMatchers(HttpMethod.DELETE, "/clientes/**").hasAuthority("admin:delete")
						
						.requestMatchers(HttpMethod.GET, "/contas/**").hasAnyAuthority("user:read", "admin:read")
						.requestMatchers(HttpMethod.POST, "/contas").hasAnyAuthority("user:write", "admin:write")
						.requestMatchers(HttpMethod.PUT, "/contas/**").hasAnyAuthority("user:write", "admin:write")
						
						.anyRequest().authenticated())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authenticationProvider(authenticationProvider)
				.exceptionHandling(ex -> ex
					.authenticationEntryPoint(jwtAuthenticationEntryPoint)
					.accessDeniedHandler(customAccessDeniedHandler)) 
				.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}
	
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		
		configuration.setAllowedOriginPatterns(Arrays.asList(
			"http://localhost:*",          
			"https://*.herokuapp.com",      
			"https://*.vercel.app"         	
			));
		
		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
		configuration.setAllowedHeaders(Arrays.asList("*"));
		configuration.setAllowCredentials(true);
		configuration.setMaxAge(3600L); 
		
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
}