package com.example.reservaSala.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class WebSecurityConfig {

    private final UserDetailsServiceImpl userDetailsService;

    public WebSecurityConfig(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .authenticationProvider(authenticationProvider())  

      .authorizeHttpRequests(auth -> auth
            .requestMatchers("/css/**").permitAll()  // Recursos estáticos liberados
            .requestMatchers("/admin/login", "/admin/cadastro").permitAll()  // Login e cadastro liberados
            .anyRequest().hasRole("ADMIN")  // TODO O RESTO: só ADMIN
        )
        .formLogin(form -> form
            .loginPage("/admin/login")
            .defaultSuccessUrl("/admin/principal", true)
            .permitAll()
        )
		.logout(logout -> logout
			.logoutUrl("/logout")
			.logoutSuccessUrl("/admin/login?logout=true")
			.permitAll()
		)
        .exceptionHandling(handler -> handler
            .accessDeniedPage("/403")
        );

    return http.build();
}
}
