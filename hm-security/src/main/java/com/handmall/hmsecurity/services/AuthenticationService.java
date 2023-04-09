package com.handmall.hmsecurity.services;

import com.handmall.hmsecurity.dtos.AuthenticationRequest;
import com.handmall.hmsecurity.dtos.AuthenticationResponse;
import com.handmall.hmsecurity.dtos.RegisterRequest;
import com.handmall.hmsecurity.entities.User;
import com.handmall.hmsecurity.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;

    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
        return null;
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        return null;
    }
}
