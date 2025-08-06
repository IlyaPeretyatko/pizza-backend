package ru.nsu.assjohns.config.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "spring.security.jwt")
public class JwtProperties {
    private String secret;
    private long access;
    private long refresh;
}
