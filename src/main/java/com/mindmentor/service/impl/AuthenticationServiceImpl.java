package com.mindmentor.service.impl;

import com.mindmentor.config.JwtService;
import com.mindmentor.exceptions.AlreadyExistsException;
import com.mindmentor.exceptions.InvalidPasswordException;
import com.mindmentor.model.Authentication;
import com.mindmentor.model.request.SignIn;
import com.mindmentor.model.request.SignUp;
import com.mindmentor.repository.UserInfoRepository;
import com.mindmentor.repository.UserRepository;
import com.mindmentor.service.AuthenticationService;
import com.example.public_.tables.records.UsersInfoRecord;
import com.example.public_.tables.records.UsersRecord;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final UserInfoRepository userInfoRepository;

    @Override
    public Authentication signUp(SignUp signUp) {
        if (userInfoRepository.existsByEmail(signUp.email())) {
            log.warn("Attempt to register with already existing email: {}", signUp.email());
            throw new AlreadyExistsException("Email already exists");
        }

        UsersInfoRecord newUserInfo = createUserInfo(signUp);
        int userInfoId = userInfoRepository.save(newUserInfo);

        UsersRecord newUser = createUser(signUp, userInfoId);
        int userId = userRepository.save(newUser, userInfoId);

        log.info("User registered successfully with ID: {}", userId);

        String jwtToken = jwtService.generateToken(newUserInfo);

        return new Authentication(
                userId,
                newUser.getName(),
                jwtToken,
                newUserInfo.getRole()
        );
    }

    @Override
    public Authentication signIn(SignIn signIn) {
        UsersInfoRecord userInfo = userInfoRepository.findByEmail(signIn.email());

        if (userInfo == null || !passwordEncoder.matches(signIn.password(), userInfo.getPassword())) {
            log.error("Invalid sign-in attempt for email: {}", signIn.email());
            throw new InvalidPasswordException("Invalid email or password");
        }

        UsersRecord user = userRepository.findUserByUserInfoId(userInfo.getId());

        String jwtToken = jwtService.generateToken(userInfo);

        return new Authentication(
                user.getId(),
                user.getName(),
                jwtToken,
                userInfo.getRole()
        );
    }

    private UsersInfoRecord createUserInfo(SignUp signUp) {
        UsersInfoRecord userInfo = new UsersInfoRecord();
        userInfo.setEmail(signUp.email());
        userInfo.setPassword(passwordEncoder.encode(signUp.password()));
        userInfo.setRole(signUp.role());
        return userInfo;
    }

    private UsersRecord createUser(SignUp signUp, int userInfoId) {
        UsersRecord user = new UsersRecord();
        user.setName(signUp.firstName());
        user.setSurname(signUp.lastName());
        user.setUsersInfoId(userInfoId);
        return user;
    }
}
