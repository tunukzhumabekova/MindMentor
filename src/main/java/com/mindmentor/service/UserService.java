package com.mindmentor.service;

import com.mindmentor.model.request.UserCreateRequest;

public interface UserService {
    void createUser(UserCreateRequest request);
    void updateUser(UserCreateRequest request);
    void deleteUser(String email);
    void blockUser(String email);
    void unblockUser(String email);
}
