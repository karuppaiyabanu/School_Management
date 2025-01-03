package com.example.schoolmanagement.config;

import com.example.schoolmanagement.filter.JwtAuthFilter;
import com.example.schoolmanagement.repository.UserInfoRepository;
import com.example.schoolmanagement.service.UserInfoUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig {

    @Autowired
    private JwtAuthFilter authFilter;
    @Autowired
    private UserInfoRepository userInfoRepository;


    @Autowired
    private AuthenticationEntryPointImplement authenticationEntryPointImplement;

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserInfoUserDetailsService(userInfoRepository);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/v1/users/**").permitAll()
                        .requestMatchers("/api/**").permitAll()
                        .requestMatchers("/api/v1/schools/**").hasAuthority("ROLE_SUPER_ADMIN")
                        .requestMatchers("/api/v1/standards/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers("/api/v1/sections/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers("/api/v1/teachers/**").hasAuthority("ROLE_HR")
                        .requestMatchers("/api/v1/students/**").hasAuthority("ROLE_OFFICE_ADMIN")
                        .requestMatchers("/api/v1/assign-teachers/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers("/api/v1/subjects/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers("/api/v1/exams/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers("/api/v1/attendances/**").hasAuthority("ROLE_TEACHER")
                        .requestMatchers("/api/v1/marks/**").hasAuthority("ROLE_TEACHER")
                        .anyRequest().authenticated()

                )

                .exceptionHandling(exceptionHandling -> exceptionHandling.authenticationEntryPoint(authenticationEntryPointImplement))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(this.authFilter, UsernamePasswordAuthenticationFilter.class)
                .authenticationProvider(authenticationProvider());
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());// password
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }


}
