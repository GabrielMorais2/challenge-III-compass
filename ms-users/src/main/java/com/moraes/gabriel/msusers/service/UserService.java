package com.moraes.gabriel.msusers.service;

import com.moraes.gabriel.msusers.exception.EmailAlreadyExistsException;
import com.moraes.gabriel.msusers.model.UserCredential;
import com.moraes.gabriel.msusers.model.payload.UserAuthenticateRequest;
import com.moraes.gabriel.msusers.model.payload.UserRequest;
import com.moraes.gabriel.msusers.model.payload.UserResponse;
import com.moraes.gabriel.msusers.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final ModelMapper mapper;
    private final PasswordEncoder encoder;


    public UserResponse registerUser(UserRequest userRequest) {
        userRequest.setPassword(encoder.encode(userRequest.getPassword()));

        UserCredential user = mapper.map(userRequest, UserCredential.class);
        validateEmailUniqueness(user.getEmail());

        return mapper.map(userRepository.save(user), UserResponse.class);
    }

    public String authenticate(UserAuthenticateRequest userAuthenticateRequest) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userAuthenticateRequest.getEmail(),
                        userAuthenticateRequest.getPassword()
                )
        );

        UserCredential user = userRepository.findByEmail(userAuthenticateRequest.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return jwtService.generateToken(user.getEmail());
    }

    public void validateEmailUniqueness(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new EmailAlreadyExistsException("Email already exists: " + email);
        }
    }

}
