package com.moraes.gabriel.msusers.controller;

import com.moraes.gabriel.msusers.model.payload.UserRequest;
import com.moraes.gabriel.msusers.model.payload.UserResponse;
import com.moraes.gabriel.msusers.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<UserResponse> register(@Valid @RequestBody UserRequest userDtoRequest){
        return new ResponseEntity<>(userService.registerUser(userDtoRequest), HttpStatus.CREATED);
    }

}
