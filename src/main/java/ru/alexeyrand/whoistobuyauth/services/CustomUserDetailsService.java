package ru.alexeyrand.whoistobuyauth.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.alexeyrand.whoistobuyauth.entities.CustomUserDetails;
import ru.alexeyrand.whoistobuybase.entities.User;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final JwtService jwtService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = jwtService.findUserByUsername(username);
        return new CustomUserDetails(user);
    }
}
