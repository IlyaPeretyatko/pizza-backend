package ru.nsu.assjohns.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static ru.nsu.assjohns.config.constant.AuthData.*;

@RequiredArgsConstructor
public class AuthTokenFilter extends OncePerRequestFilter {

    private final AuthTokenProvider authTokenProvider;

    @Override
    public void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        String bearerToken = request.getHeader(AUTH_HEADER_NAME);
        if (bearerToken != null && bearerToken.startsWith(BEARER_PREFIX)) {
            bearerToken = bearerToken.substring(7);
        }
        if (bearerToken != null && authTokenProvider.isValid(bearerToken)) {
            Authentication authentication = authTokenProvider.getAuthentication(bearerToken);
            if (authentication != null) {
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);
    }

}