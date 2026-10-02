package com.airline.reservation.config;

import com.airline.reservation.service.UserDetailsServiceImpl;
import com.airline.reservation.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;

    public JwtAuthFilter(JwtUtil jwtUtil, UserDetailsServiceImpl userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        try {
            final String authHeader = request.getHeader("Authorization");
            
            // Skip authentication for OPTIONS requests and public endpoints
            if (request.getMethod().equals("OPTIONS") || 
                request.getServletPath().contains("/api/auth/") ||
                request.getServletPath().contains("/api/flights/search") ||
                request.getServletPath().contains("/api/flights/airports")) {
                filterChain.doFilter(request, response);
                return;
            }

            // Check if request has a valid Authorization header
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");
                response.getWriter().write("{\"error\": \"Missing or invalid Authorization header\"}");
                return;
            }

            final String jwt = authHeader.substring(7);
            final String username;
            try {
                username = jwtUtil.extractUsername(jwt);
                System.out.println("Extracted username from token: " + username);
            } catch (Exception e) {
                System.err.println("Error extracting username from token: " + e.getMessage());
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");
                response.getWriter().write("{\"error\": \"Invalid JWT token: " + e.getMessage() + "\"}");
                return;
            }

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                try {
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                    System.out.println("Loaded user details for: " + username);
                    System.out.println("User authorities: " + userDetails.getAuthorities());
                    
                    if (jwtUtil.isTokenValid(jwt, userDetails)) {
                        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );
                        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                        System.out.println("Authentication successful for user: " + username);
                    } else {
                        System.err.println("Token validation failed for user: " + username);
                        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        response.setContentType("application/json");
                        response.getWriter().write("{\"error\": \"Invalid or expired token\"}");
                        return;
                    }
                } catch (UsernameNotFoundException e) {
                    System.err.println("User not found: " + username);
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.setContentType("application/json");
                    response.getWriter().write("{\"error\": \"User not found: " + e.getMessage() + "\"}");
                    return;
                }
            }

            filterChain.doFilter(request, response);
        } catch (Exception e) {
            System.err.println("Authentication error: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"Authentication failed: " + e.getMessage() + "\"}");
        }
    }
}