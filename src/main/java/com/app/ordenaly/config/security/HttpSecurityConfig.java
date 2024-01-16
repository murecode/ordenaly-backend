package com.app.ordenaly.config.security;

import com.app.ordenaly.utils.Permissions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import java.security.Permission;

@Configuration
@EnableWebSecurity
public class HttpSecurityConfig {
  @Autowired
  private AuthenticationProvider authenticationProvider;
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
            .csrf(csrfConfig -> csrfConfig.disable()) //1.
            .sessionManagement( sessionMangConfig -> sessionMangConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) //2.
            .authenticationProvider(authenticationProvider)
            .authorizeHttpRequests((authorize) -> {

              authorize.requestMatchers(HttpMethod.POST,  "/api/v1/auth/login").permitAll();
              authorize.requestMatchers(HttpMethod.POST,  "/api/v1/auth/signup").permitAll();
              authorize.requestMatchers("/error").permitAll();

              authorize.requestMatchers(HttpMethod.GET,    "/api/v1/orders").permitAll();
              authorize.requestMatchers(HttpMethod.GET,    "/api/v1/orders/{id}").permitAll();
              authorize.requestMatchers(HttpMethod.POST,   "/api/v1/orders/{id}/add-item/**").permitAll();
              authorize.requestMatchers(HttpMethod.POST,   "/api/v1/orders/**").hasAnyRole("ADMIN", "STAFF");
              authorize.requestMatchers(HttpMethod.PUT,    "/api/v1/orders/{id}").permitAll();
              authorize.requestMatchers(HttpMethod.DELETE, "/api/v1/orders/{id}").hasAnyRole("ADMIN", "STAFF");
              authorize.requestMatchers(HttpMethod.DELETE, "/api/v1/orders/item/{id}").hasAnyRole("ADMIN", "STAFF");

//              authorize.requestMatchers(HttpMethod.GET, "/api/v1/items/{id}").permitAll();
              authorize.requestMatchers(HttpMethod.PUT, "/api/v1/items/{id}").permitAll();

              authorize.requestMatchers(HttpMethod.GET, "/api/v1/products").permitAll();
              authorize.requestMatchers(HttpMethod.GET, "/api/v1/products/{id}").permitAll();
              authorize.requestMatchers(HttpMethod.POST,"/api/v1/products").permitAll();
              authorize.requestMatchers(HttpMethod.PUT, "/api/v1/products/{id}").hasRole("ADMIN");
              authorize.requestMatchers(HttpMethod.DELETE, "/api/v1/products/{id}").permitAll();

              authorize.requestMatchers(HttpMethod.GET, "/api/v1/tickets").permitAll();
              authorize.requestMatchers(HttpMethod.POST,"/api/v1/tickets").permitAll();

              authorize.requestMatchers(HttpMethod.GET,   "/api/v1/users").permitAll();
              authorize.requestMatchers(HttpMethod.POST,  "/api/v1/users").hasRole(Permissions.SAVE_USER.name());
              authorize.requestMatchers(HttpMethod.PUT,   "/api/v1/users/{id}").permitAll();
              authorize.requestMatchers(HttpMethod.DELETE,"/api/v1/users/{id}").hasRole(Permissions.DELETE_USER.name());

              authorize.requestMatchers("/v1/authenticate", "/v3/api-docs/**", "swagger-ui/**","/swagger-ui.html").permitAll();

              authorize.requestMatchers(HttpMethod.DELETE,"/api/v1/users/{id}").hasAuthority(Permissions.DELETE_USER.name());
              authorize.requestMatchers(HttpMethod.GET,   "/api/v1/products").hasAuthority(Permissions.DELETE_PRODUCT.name());
              authorize.requestMatchers(HttpMethod.GET,   "/api/v1/orders").hasAuthority(Permissions.READ_ORDERS.name());

              authorize.anyRequest().denyAll();
            });

    return http.build();
  }

}

//1. Deshabilitar CORS
//2.[Se encarga de configurar el manejo de sesiones en la aplicación. La aplicación no mantendrá...
// el estado de la sesión en el servidor y cada solicitud se manejará de manera independiente sin...
// depender del estado de la sesión. Esto es útil en escenarios donde se prefiere la...
// arquitectura sin estado, comúnmente asociada con aplicaciones RESTful y servicios web.]
