package ru.nsu.assjohns.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.nsu.assjohns.grpc.AuthServiceGrpc;
import ru.nsu.assjohns.grpc.UserRequest;
import ru.nsu.assjohns.grpc.UserResponse;

import java.io.IOException;

import static ru.nsu.assjohns.config.constant.AuthData.AUTH_HEADER_NAME;
import static ru.nsu.assjohns.config.constant.AuthData.BEARER_PREFIX;

@RequiredArgsConstructor
public class AuthTokenFilter extends OncePerRequestFilter {

    private final AuthServiceGrpc.AuthServiceBlockingStub authStub;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        String token = request.getHeader(AUTH_HEADER_NAME);
        if (token != null && token.startsWith(BEARER_PREFIX)) {
            token = token.substring(7);
            UserRequest userRequest = UserRequest.newBuilder()
                    .setToken(token)
                    .build();
            UserResponse userResponse = authStub.getUser(userRequest);
            Authentication auth = new UsernamePasswordAuthenticationToken(
                    userResponse.getId(),
                    null,
                    userResponse.getRolesList()
                            .stream()
                            .map(SimpleGrantedAuthority::new)
                            .toList()
            );
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        filterChain.doFilter(request, response);
    }

}