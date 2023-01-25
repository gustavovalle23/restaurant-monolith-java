package com.gustavovalle.medicalappointment.infra.service.security;


import com.gustavovalle.medicalappointment.infra.repositories.UserRepositoryJpa;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class AuthenticationService implements UserDetailsService {

    private final UserRepositoryJpa userRepository;

    public AuthenticationService(UserRepositoryJpa userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found with provided credentials"));
    }

    public Authentication authenticate(AuthenticationManager authenticationManager,
                                       UsernamePasswordAuthenticationToken login) throws Exception {
        try {
            return authenticationManager.authenticate(login);
        } catch (DisabledException e) {
            throw new Exception("User Disabled");
        } catch (BadCredentialsException e) {
            throw new Exception("Invalid Credentials");
        }
    }

}
