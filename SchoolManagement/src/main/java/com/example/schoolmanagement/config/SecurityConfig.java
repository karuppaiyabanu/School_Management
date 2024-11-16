package com.example.schoolmanagement.config;

import com.example.schoolmanagement.filter.JwtAuthFilter;
import com.example.schoolmanagement.repository.UserInfoRepository;
import com.example.schoolmanagement.service.UserInfoUserDetailsService;
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
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthFilter authFilter;
    private final UserInfoRepository userInfoRepository;

    public SecurityConfig(final JwtAuthFilter jwtAuthFilter, final UserInfoRepository userInfoRepository) {
        this.authFilter = jwtAuthFilter;
        this.userInfoRepository = userInfoRepository;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserInfoUserDetailsService(userInfoRepository);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable).authorizeHttpRequests(auth -> auth.requestMatchers("/auth/user/**", "/api/subject/**").permitAll()
                .requestMatchers("/api/school/**").hasAnyAuthority("ROLE_SUPER_ADMIN")
                .requestMatchers("/api/standard/**").hasAnyAuthority("ROLE_ADMIN")
                .requestMatchers("/api/section/**").hasAnyAuthority("ROLE_ADMIN")
                .requestMatchers("/api/teacher/**").hasAnyAuthority("ROLE_HR")
                .requestMatchers("/api/students/**").hasAnyAuthority("ROLE_OFFICE_ADMIN")
                .requestMatchers("/api/assign/**").hasAnyAuthority("ROLE_ADMIN")
                .requestMatchers("/api/subject/**").hasAnyAuthority("ROLE_ADMIN")
                .requestMatchers("/api/exam/**").hasAnyAuthority("ROLE_ADMIN")
                .requestMatchers("/api/attendance/**").hasAnyAuthority("ROLE_TEACHER")
                .requestMatchers("/api/mark/**").hasAnyAuthority("ROLE_TEACHER"))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider()).addFilterBefore(this.authFilter, UsernamePasswordAuthenticationFilter.class); // I can't underStand It that Line

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());  ///user tables all values are there
        authenticationProvider.setPasswordEncoder(passwordEncoder());// password
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

}
