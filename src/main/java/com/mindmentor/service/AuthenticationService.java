package com.mindmentor.service;

import com.mindmentor.model.Authentication;
import com.mindmentor.model.request.SignIn;
import com.mindmentor.model.request.SignUp;

public interface AuthenticationService {
    Authentication signUp(SignUp signUp);
    Authentication signIn(SignIn signIn);
}