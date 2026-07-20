package com.example.HealthCareApp.Security;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtFilter;
    private final CustomUserDetailsService userDetailsService;

    public SecurityConfig(JwtAuthenticationFilter jwtFilter,
                          CustomUserDetailsService userDetailsService) {
        this.jwtFilter = jwtFilter;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(csrf -> csrf.disable());
        http.cors(cors -> {});

        http.sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        http.exceptionHandling(exception -> exception

                .authenticationEntryPoint((request, response, authException) -> {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");

                    response.getWriter().write("""
                            {
                              "status": 401,
                              "error": "Unauthorized",
                              "message": "Authentification requise ou token invalide"
                            }
                            """);
                })

                .accessDeniedHandler((request, response, accessDeniedException) -> {
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");

                    response.getWriter().write("""
                            {
                              "status": 403,
                              "error": "Forbidden",
                              "message": "Accès refusé : vous n’avez pas l’autorisation"
                            }
                            """);
                })
        );

        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/auth/**").permitAll()
                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html").permitAll()

                .requestMatchers(HttpMethod.GET, "/patients/me").hasRole("PATIENT")
                .requestMatchers(HttpMethod.GET, "/medcins/me").hasRole("MEDECIN")
                .requestMatchers(HttpMethod.GET, "/dossiers/me").hasRole("PATIENT")
                .requestMatchers(HttpMethod.GET, "/rendezvous/me").hasAnyRole("PATIENT", "MEDECIN", "ADMIN")

                .requestMatchers("/users/**").hasRole("ADMIN")

                .requestMatchers(HttpMethod.POST, "/patients/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/patients/**").hasAnyRole("ADMIN", "PATIENT")
                .requestMatchers(HttpMethod.DELETE, "/patients/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/patients/**").hasAnyRole("ADMIN", "MEDECIN")
                .requestMatchers(HttpMethod.GET,"/patients/findbytel").hasAnyRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/medcins/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/medcins/**").hasAnyRole("ADMIN", "MEDECIN")
                .requestMatchers(HttpMethod.DELETE, "/medcins/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/medcins/**").hasAnyRole("ADMIN", "PATIENT")

                .requestMatchers(HttpMethod.POST, "/rendezvous/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/rendezvous/**").hasAnyRole("ADMIN", "MEDECIN")
                .requestMatchers(HttpMethod.DELETE, "/rendezvous/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/rendezvous/**").hasRole("ADMIN")

                .requestMatchers(HttpMethod.POST, "/dossiers/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/dossiers/**").hasAnyRole("ADMIN", "MEDECIN")
                .requestMatchers(HttpMethod.DELETE, "/dossiers/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/dossiers/**").hasAnyRole("ADMIN", "MEDECIN")

                .requestMatchers(HttpMethod.GET,    "/api/download/**").hasAnyRole("ADMIN","MEDECIN","PATIENT")


                 .anyRequest().authenticated()
        );

        http.authenticationProvider(authenticationProvider());

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
