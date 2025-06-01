package ru.alexeyrand.whoistobuyauth.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.alexeyrand.whoistobuyauth.dtos.SignInRequest;
import ru.alexeyrand.whoistobuyauth.dtos.SignInResponse;
import ru.alexeyrand.whoistobuyauth.dtos.SignUpRequest;
import ru.alexeyrand.whoistobuybase.entities.User;
import ru.alexeyrand.whoistobuybase.services.UserService;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public User register(SignUpRequest request) {
        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .build();
        return userService.save(user);
    }

    public SignInResponse verify(SignInRequest request) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        SignInResponse response = new SignInResponse();
        response.setToken(authentication.isAuthenticated() ?
                jwtService.generateToken(request.getUsername()) :
                "Fail");
        response.setUsername(request.getUsername());
        if (!Objects.equals(response.getToken(), "Fail")) {
            return response;
        } else {
            return new SignInResponse();
        }
    }
}
