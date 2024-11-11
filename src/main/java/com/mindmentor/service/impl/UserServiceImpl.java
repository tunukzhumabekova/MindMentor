package com.mindmentor.service.impl;

import com.mindmentor.model.request.UserCreateRequest;
import com.mindmentor.repository.UserInfoRepository;
import com.mindmentor.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserInfoRepository userInfoRepository;

    @Override
    public void createUser(UserCreateRequest request) {
        if (emailExists(request.email())) {
            throw new IllegalArgumentException("Email already in use");
        }
        userInfoRepository.addUser(request);
    }

    @Override
    public void updateUser(UserCreateRequest request) {
        if (!emailExists(request.email())) {
            throw new IllegalArgumentException("Email not found");
        }
        userInfoRepository.updateUser(request);
    }

    @Override
    public void deleteUser(String email) {
        if (!emailExists(email)) {
            throw new IllegalArgumentException("Email not found");
        }
        userInfoRepository.deleteUser(email);
    }

    @Override
    public void blockUser(String email) {
        if (!emailExists(email)) {
            throw new IllegalArgumentException("Email not found");
        }
        userInfoRepository.blockUser(email);
    }

    @Override
    public void unblockUser(String email) {
        if (!emailExists(email)) {
            throw new IllegalArgumentException("Email not found");
        }
        userInfoRepository.unblockUser(email);
    }

    private boolean emailExists(String email) {
        return userInfoRepository.existsByEmail(email);
    }
}
