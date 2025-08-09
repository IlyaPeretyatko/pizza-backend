package ru.nsu.assjohns.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.grpc.server.service.GrpcService;
import ru.nsu.assjohns.dto.user.UserGetResponse;
import ru.nsu.assjohns.grpc.AuthServiceGrpc;
import ru.nsu.assjohns.grpc.UserRequest;
import ru.nsu.assjohns.grpc.UserResponse;
import ru.nsu.assjohns.service.UserService;


@GrpcService
@RequiredArgsConstructor
public class GrpcController extends AuthServiceGrpc.AuthServiceImplBase {

    private final UserService userService;

    public void getUser(UserRequest request, io.grpc.stub.StreamObserver<UserResponse> responseObserver) {
        String token = request.getToken();
        UserGetResponse userGetResponse = userService.getUser(token);
        UserResponse userResponse = UserResponse.newBuilder()
                .setId(userGetResponse.getId())
                .setName(userGetResponse.getName())
                .setEmail(userGetResponse.getEmail())
                .addAllRoles(userGetResponse.getRoles())
                .build();
        responseObserver.onNext(userResponse);
        responseObserver.onCompleted();
    }
}
