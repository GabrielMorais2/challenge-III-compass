package com.moraes.gabriel.msusers.service;

import Utils.JsonUtils;
import com.moraes.gabriel.msusers.exception.EmailAlreadyExistsException;
import com.moraes.gabriel.msusers.model.UserCredential;
import com.moraes.gabriel.msusers.model.payload.UserAuthenticateRequest;
import com.moraes.gabriel.msusers.model.payload.UserRequest;
import com.moraes.gabriel.msusers.model.payload.UserResponse;
import com.moraes.gabriel.msusers.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private PasswordEncoder encoder;

    @Mock
    private JwtService jwtService;

    @Spy
    private ModelMapper mapper;

    private static final String USER_REQUEST = "/Payload/USER_REQUEST.json";
    private static final String USER_CREDENTIAL = "/Payload/USER_CREDENTIAL.json";
    private static final String USER_AUTHENTICATION_REQUEST = "/Payload/USER_AUTHENTICATION_REQUEST.json";

    @Test
    public void registerUser_ReturAnUserResponse() throws IOException {
        UserRequest userRequest = JsonUtils.getObjectFromFile(USER_REQUEST, UserRequest.class);
        UserCredential user = JsonUtils.getObjectFromFile(USER_CREDENTIAL, UserCredential.class);

        when(encoder.encode("12345678")).thenReturn("encodedPassword");
        when(userRepository.existsByEmail(any())).thenReturn(false);
        when(userRepository.save(any())).thenReturn(user);

        UserResponse response = userService.registerUser(userRequest);

        assertNotNull(response);
        assertEquals(user.getId(), response.getId());
        assertEquals(user.getEmail(), response.getEmail());
        verify(encoder).encode("12345678");
    }

    @Test
    public void registerUser_WithExistingEmail_RetuanAnEmailAlreadyExistsException() throws IOException {
        UserRequest userRequest = JsonUtils.getObjectFromFile(USER_REQUEST, UserRequest.class);

        when(userRepository.existsByEmail(any())).thenReturn(true);

        assertThrows(EmailAlreadyExistsException.class, () -> userService.registerUser(userRequest));

        verify(userRepository).existsByEmail("Gabriel@test");
        verify(userRepository, times(0)).save(any());
    }

    @Test
    public void authenticateUser_ReturnAnJwtToken() throws IOException {
        UserAuthenticateRequest userAuthenticateRequest = JsonUtils.getObjectFromFile(USER_AUTHENTICATION_REQUEST, UserAuthenticateRequest.class);
        UserCredential user = JsonUtils.getObjectFromFile(USER_CREDENTIAL, UserCredential.class);;

        when(userRepository.findByEmail(any())).thenReturn(Optional.of(user));
        when(jwtService.generateToken(any())).thenReturn("894E635266236A586E3272357538782F413F4428472B4B6250645367566B5970");

        String jwtToken = userService.authenticate(userAuthenticateRequest);

        assertNotNull(jwtToken);
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }

    @Test
    public void authenticateUser_WithNotFoundUser_ReturnAnUsernameNotFoundException() throws IOException {
        UserAuthenticateRequest userAuthenticateRequest = JsonUtils.getObjectFromFile(USER_AUTHENTICATION_REQUEST, UserAuthenticateRequest.class);

        when(userRepository.findByEmail(any())).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> userService.authenticate(userAuthenticateRequest));
    }

}