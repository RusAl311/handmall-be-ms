package com.handmall.hmsecurity.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.handmall.hmsecurity.dtos.AuthenticationRequest;
import com.handmall.hmsecurity.dtos.AuthenticationResponse;
import com.handmall.hmsecurity.dtos.RegisterRequest;
import com.handmall.hmsecurity.entities.Role;
import com.handmall.hmsecurity.entities.Token;
import com.handmall.hmsecurity.entities.TokenType;
import com.handmall.hmsecurity.entities.User;
import com.handmall.hmsecurity.repositories.TokenRepository;
import com.handmall.hmsecurity.repositories.UserRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
	private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        var username = userRepository.findByEmail(request.getEmail());
        if (username.isPresent()){
            throw new IllegalStateException("username is exist");
        }
        var user = User
            .builder()
            .firstname(request.getFirstname())
            .lastname(request.getLastname())
            .email(request.getEmail())
            .password(passwordEncoder.encode(request.getPassword()))
            .role(Role.USER)
            .build();
        
		var savedUser = userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
		var refreshToken = jwtService.generateRefreshToken(user);
		saveUserToken(savedUser, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
				.refreshToken(refreshToken)
                .build();
    }

    public AuthenticationResponse authenticate (AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Username not found - " + request.getEmail()));
        var jwtToken = jwtService.generateToken(user);
		var refreshToken = jwtService.generateRefreshToken(user);
		revokeAllUserTokens(user);
		saveUserToken(user, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
				.refreshToken(refreshToken)
                .build();
    }

	private void saveUserToken(User user, String jwtToken) {
		var token = Token.builder()
			.user(user)
			.token(jwtToken)
			.tokenType(TokenType.BEARER)
			.expired(false)
			.revoked(false)
			.build();
		tokenRepository.save(token);
	}

	private void revokeAllUserTokens(User user) {
		var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
		if (validUserTokens.isEmpty())
		  return;
		validUserTokens.forEach(token -> {
		  token.setExpired(true);
		  token.setRevoked(true);
		});
		tokenRepository.saveAll(validUserTokens);
	}

	public void refreshToken(
		HttpServletRequest request, 
		HttpServletResponse response
	) throws IOException {
		final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
    	final String refreshToken;
    	final String userEmail;
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			return;
		}
		refreshToken = authHeader.substring(7);
		userEmail = jwtService.extractUsername(refreshToken);
		if (userEmail != null) {
			var user = this.userRepository.findByEmail(userEmail)
					.orElseThrow(() -> new UsernameNotFoundException("Username not found - " + userEmail));
			if (jwtService.isTokenValid(refreshToken, user)) {
			  var accessToken = jwtService.generateToken(user);
			  revokeAllUserTokens(user);
			  saveUserToken(user, accessToken);
			  var authResponse = AuthenticationResponse.builder()
					  .accessToken(accessToken)
					  .refreshToken(refreshToken)
					  .build();
			  new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
			}
		}
	}
}
