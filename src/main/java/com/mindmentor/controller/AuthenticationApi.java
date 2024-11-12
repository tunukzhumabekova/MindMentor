package com.mindmentor.controller;

import com.mindmentor.model.Authentication;
import com.mindmentor.model.request.SignIn;
import com.mindmentor.model.request.SignUp;
import com.mindmentor.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/authentication")
@Tag(name = "Auth api", description = "Authentication endpoints")
@PermitAll
public class AuthenticationApi {

    private final AuthenticationService authenticationService;

    @PostMapping("/sign-up")
    @Operation(summary = "Sign Up", description = "Registers a new user with the provided sign-up details. This endpoint creates a new user account in the system.")
    public Authentication signUp(
            @Valid @RequestBody SignUp signUp) {
        return authenticationService.signUp(signUp);
    }

    @PostMapping("/sign-in")
    @Operation(summary = "Sign In", description = "Authenticates the user and returns an authentication response, which includes an access token and other relevant information. The user must provide their valid sign-in credentials.")
    public Authentication signIn(
            @RequestBody SignIn signIn) {
        return authenticationService.signIn(signIn);
    }
}