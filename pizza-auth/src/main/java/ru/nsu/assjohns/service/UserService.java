package ru.nsu.assjohns.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nsu.assjohns.config.type.MailType;
import ru.nsu.assjohns.dto.user.UserGetResponse;
import ru.nsu.assjohns.dto.user.UserPatchRequest;
import ru.nsu.assjohns.dto.user.UserPostRequest;
import ru.nsu.assjohns.dto.user.UserPostResponse;
import ru.nsu.assjohns.error.exception.ServiceException;
import ru.nsu.assjohns.mapper.UserMapper;
import ru.nsu.assjohns.entity.Role;
import ru.nsu.assjohns.entity.User;
import ru.nsu.assjohns.dao.UserRepository;
import ru.nsu.assjohns.security.AuthTokenProvider;

import java.util.Set;
import java.util.UUID;


@Service
public class UserService {

    private final UserRepository userRepository;

    private final MailService mailService;

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    private final AuthTokenProvider authTokenProvider;

    @Autowired
    public UserService(UserRepository userRepository, @Lazy PasswordEncoder passwordEncoder, MailService mailService,
                       UserMapper userMapper, @Lazy AuthTokenProvider authTokenProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.mailService = mailService;
        this.userMapper = userMapper;
        this.authTokenProvider = authTokenProvider;
    }

    @Transactional
    public UserGetResponse getUser(String token) {
        if (!authTokenProvider.isValid(token)) {
            throw new ServiceException(401, "Unauthorized.");
        }
        long id = authTokenProvider.getId(token);
        return userMapper.toUserGetResponse(getUserById(id));
    }

    @Transactional
    public UserPostResponse createUser(UserPostRequest userPostRequest) {
        User user = userMapper.toUser(userPostRequest);
        Set<Role> roles = Set.of(Role.ROLE_USER);
        user.setRoles(roles);
        User createdUser = userRepository.save(user);
        mailService.sendEmail(createdUser, MailType.REGISTER);
        return userMapper.toUserPostResponse(createdUser);
    }

    @Transactional
    public boolean verifyEmail(String token) {
        User user = userRepository.findByVerificationCode(token);
        if (user != null) {
            user.setEmailConfirmed(true);
            user.setVerificationCode(null);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    @Transactional
    public void requestResetPassword(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ServiceException(404, "User wasn't found."));
        if (!user.isEmailConfirmed()) throw new ServiceException(403, "Email not confirmed.");
        user.setVerificationCode(UUID.randomUUID().toString());
        userRepository.save(user);
        mailService.sendEmail(user, MailType.RESET_PASSWORD);
    }

    @Transactional
    public void resetPassword(String token, UserPatchRequest userPatchRequest) {
        User user = userRepository.findByVerificationCode(token);
        if (user != null) {
            user.setVerificationCode(null);
            user.setPassword(passwordEncoder.encode(userPatchRequest.getPassword()));
            userRepository.save(user);
        }
    }

    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Transactional(readOnly = true)
    public boolean existsByName(String email) {
        return userRepository.existsByUsername(email);
    }

    @Transactional(readOnly = true)
    public User getUserByName(String name) {
        return userRepository.findByUsername(name).orElseThrow(() -> new ServiceException(404, "User wasn't found."));
    }

    @Transactional(readOnly = true)
    public User getUserById(long id) {
        return userRepository.findById(id).orElseThrow(() -> new ServiceException(404, "User wasn't found."));
    }


}