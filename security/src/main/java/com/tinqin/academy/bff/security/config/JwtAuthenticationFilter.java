package com.tinqin.academy.bff.security.config;

import com.tinqin.academy.bff.business.operations.authentication.CustomUserDetailsService;
import com.tinqin.academy.bff.business.operations.authentication.JwtService;
import com.tinqin.academy.bff.domain.ClientInterpreter;
import com.tinqin.academy.piim.api.token.findbytoken.FindByTokenInput;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;
    private final ClientInterpreter clientInterpreter;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        //        if (request.getServletPath().contains("/api/v1/auth")) {
        //      filterChain.doFilter(request, response);
        //      return;
        //    }
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        jwt = authHeader.substring(7);
        userEmail = jwtService.extractUsername(jwt);
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
            boolean isTokenValid = clientInterpreter
                    .findByToken(FindByTokenInput.builder().token(jwt).build())
                    .map(t -> !t.getTokenOutput().isExpired()
                            && !t.getTokenOutput().isRevoked())
                    .getOrElse(false);
            if (jwtService.isTokenValid(jwt, userDetails) && isTokenValid) {
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                //                authToken.setDetails(
                //                        new WebAuthenticationDetailsSource().buildDetails(request)
                //                );
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
