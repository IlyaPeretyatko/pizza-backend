package ru.nsu.assjohns.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import ru.nsu.assjohns.config.property.JwtProperties;
import ru.nsu.assjohns.dto.auth.AuthResponse;
import ru.nsu.assjohns.error.exception.ServiceException;
import ru.nsu.assjohns.entity.Role;
import ru.nsu.assjohns.entity.User;
import ru.nsu.assjohns.service.UserService;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthTokenProvider {

    private final JwtProperties jwtProperties;

    private final UserDetailsService userDetailsService;

    private final UserService userService;

    private SecretKey key;

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes());
    }

    public String createAccessToken(long id, String username, Set<Role> roles) {
        Claims claims = Jwts.claims()
                .subject(username)
                .add("id", id)
                .add("roles", resolveRoles(roles))
                .build();
        Instant validity = Instant.now()
                .plus(jwtProperties.getAccess(), ChronoUnit.HOURS);
        return Jwts.builder()
                .claims(claims)
                .expiration(Date.from(validity))
                .signWith(key)
                .compact();
    }

    private List<String> resolveRoles(final Set<Role> roles) {
        return roles.stream()
                .map(Enum::name)
                .collect(Collectors.toList());
    }

    public String createRefreshToken(long id, String username) {
        Claims claims = Jwts.claims()
                .subject(username)
                .add("id", id)
                .build();
        Instant validity = Instant.now()
                .plus(jwtProperties.getRefresh(), ChronoUnit.DAYS);
        return Jwts.builder()
                .claims(claims)
                .expiration(Date.from(validity))
                .signWith(key)
                .compact();
    }

    public AuthResponse refreshToken(String refreshToken) {
        AuthResponse authResponse = new AuthResponse();
        if (!isValid(refreshToken)) {
            throw new ServiceException(HttpStatus.FORBIDDEN.value(), "Access denied.");
        }
        long id = getId(refreshToken);
        User user = userService.getUserById(id);
        authResponse.setId(id);
        authResponse.setUsername(user.getUsername());
        authResponse.setAccessToken(
                createAccessToken(id, user.getUsername(), user.getRoles())
        );
        authResponse.setRefreshToken(
                createRefreshToken(id, user.getUsername())
        );
        return authResponse;
    }

    public boolean isValid(String token) {
        Jws<Claims> claims = Jwts
                .parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token);
        return claims.getPayload()
                .getExpiration()
                .after(new Date());
    }

    public Long getId(String token) {
        return Jwts
                .parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("id", Long.class);
    }

    public Authentication getAuthentication(String token) {
        String username = getUsername(token);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );
    }

    private String getUsername(String token) {
        return Jwts
                .parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

}
