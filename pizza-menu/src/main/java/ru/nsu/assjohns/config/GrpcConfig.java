package ru.nsu.assjohns.config;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.nsu.assjohns.grpc.AuthServiceGrpc;

@Configuration
public class GrpcConfig {
    @Value("${auth.grpc.host}")
    private String host;

    @Value("${auth.grpc.port}")
    private int port;

    @Bean
    public ManagedChannel managedChannel() {
        return ManagedChannelBuilder
                .forAddress(host, port)
                .usePlaintext()
                .build();
    }

    @Bean
    public AuthServiceGrpc.AuthServiceBlockingStub authServiceBlockingStub(ManagedChannel channel) {
        return AuthServiceGrpc.newBlockingStub(channel);
    }
}
