package ru.tbank.backend.config.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;
import ru.tbank.backend.config.userDetails.CustomUserDetailsService;
import ru.tbank.backend.service.JwtService;

import java.io.IOException;

@Lazy
@Component
public class JwtAuthenticationFilter extends BasicAuthenticationFilter {

    private static final String AUTHORIZATION_HEADER = "Authorization";

    private static final String TOKEN_PREFIX = "Bearer ";

    private final JwtService jwtService;

    private final CustomUserDetailsService userService;

    public JwtAuthenticationFilter(
            AuthenticationManager authenticationManager,
            JwtService jwtService,
            CustomUserDetailsService userService
    ) {
        super(authenticationManager);
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        String authHeaderValue = request.getHeader(AUTHORIZATION_HEADER);
        String token = null;
        String username = null;

        if (authHeaderValue != null && authHeaderValue.startsWith(TOKEN_PREFIX)) {
            token = authHeaderValue.substring(TOKEN_PREFIX.length());
            username = jwtService.extractUserName(token);
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userService.loadUserByUsername(username);

            if (jwtService.isTokenValid(token, userDetails) && userDetails.isAccountNonLocked()) {
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, token, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}
