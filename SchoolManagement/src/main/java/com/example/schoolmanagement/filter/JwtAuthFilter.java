package com.example.schoolmanagement.filter;

import com.example.schoolmanagement.service.JwtService;
import com.example.schoolmanagement.service.UserInfoUserDetailsService;
import com.example.schoolmanagement.util.TokenExtractor;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserInfoUserDetailsService userInfoUserDetailsService;

    public JwtAuthFilter(JwtService jwtService, UserInfoUserDetailsService userInfoUserDetailsService) {
        this.jwtService = jwtService;
        this.userInfoUserDetailsService = userInfoUserDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {

            System.out.println("login to work");
            token = authHeader.substring(7);
            username = jwtService.extractUsername(token);
           //System.out.println(username);
            //TokenExtractor.printTokenDetails(token);
        }
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            System.out.println("after work");
            UserDetails userDetails = userInfoUserDetailsService.loadUserByUsername(username);
            if (jwtService.validateToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }

}
