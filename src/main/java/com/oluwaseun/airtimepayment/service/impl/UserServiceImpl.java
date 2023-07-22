package com.oluwaseun.airtimepayment.service.impl;

import com.github.dockerjava.api.exception.UnauthorizedException;
import com.oluwaseun.airtimepayment.Exception.DuplicateEntityException;
import com.oluwaseun.airtimepayment.domain.ApplicationUser;
import com.oluwaseun.airtimepayment.domain.UserRole;
import com.oluwaseun.airtimepayment.dto.UserAuthResponse;
import com.oluwaseun.airtimepayment.dto.SignUpRequest;
import com.oluwaseun.airtimepayment.repository.UserRepository;
import com.oluwaseun.airtimepayment.security.JwtTokenProvider;
import com.oluwaseun.airtimepayment.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    @Override
    public UserAuthResponse signIn(String username, String password) {
        try {
            // attempt authentication
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

            //if successful, set authentication object in the security context
            SecurityContextHolder.getContext().setAuthentication(authentication);

            //generate jwt token
            List<UserRole> userRoles = userRepository.findByUsername(username).getUserRoles();
            String token = jwtTokenProvider.generateToken(username, userRoles);
            return UserAuthResponse.builder().username(username).userRoles(userRoles).accessToken(token).build();
        } catch (AuthenticationException e) {
            log.info("Incorrect user credentials");
            throw new UnauthorizedException(e.getMessage());
        }
    }

    @Override
    public UserAuthResponse signup(SignUpRequest request) {
        log.info("processing sign up request");

        //check if user exists
        if (!userRepository.existsByUsername(request.getUsername())) {

            //save user
            userRepository.save(ApplicationUser.builder()
                    .email(request.getUsername())
                    .username(request.getUsername())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .userRoles(request.getUserRoles())
                    .build());

            //generate access token
            String token = jwtTokenProvider.generateToken(request.getUsername(), request.getUserRoles());

            log.info("done processing sign up request");
            return UserAuthResponse.builder().username(request.getUsername()).userRoles(request.getUserRoles()).accessToken(token).build();

        } else {
            throw new DuplicateEntityException("Username is already in use");
        }
    }

    @Override
    public List<String> getUserRoles() {
        return Arrays.stream(UserRole.values())
                .map(UserRole::name)
                .collect(Collectors.toList());
    }
}
