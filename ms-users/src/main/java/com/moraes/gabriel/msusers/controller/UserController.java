package com.moraes.gabriel.msusers.controller;

import com.moraes.gabriel.msusers.model.payload.UserAuthenticateRequest;
import com.moraes.gabriel.msusers.model.payload.UserRequest;
import com.moraes.gabriel.msusers.model.payload.UserResponse;
import com.moraes.gabriel.msusers.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody UserRequest userRequest) {
        UserResponse userResponse = userService.registerUser(userRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<String> authenticate(@Valid @RequestBody UserAuthenticateRequest userAuthenticateRequest) {
        String jwtToken = userService.authenticate(userAuthenticateRequest);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + jwtToken);
        return ResponseEntity.ok().headers(headers).body("Successfully authenticated user");
    }

}

