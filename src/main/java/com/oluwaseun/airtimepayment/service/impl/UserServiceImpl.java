package com.oluwaseun.airtimepayment.service.impl;

import com.oluwaseun.airtimepayment.Exception.DuplicateEntityException;
import com.oluwaseun.airtimepayment.domain.ApplicationUser;
import com.oluwaseun.airtimepayment.domain.UserRole;
import com.oluwaseun.airtimepayment.dto.SignInResponse;
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
    public SignInResponse signIn(String username, String password) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            List<UserRole> userRoles = userRepository.findByUsername(username).getUserRoles();
            String token = jwtTokenProvider.generateToken(username, userRoles);
            return SignInResponse.builder().username(username).userRoles(userRoles).accessToken(token).build();
        } catch (AuthenticationException e) {
            log.error("");
        }
        return null;
    }

    @Override
    public SignInResponse signup(SignUpRequest request) {
        log.info("processing sign up request");
        if (!userRepository.existsByUsername(request.getUsername())) {

            userRepository.save(ApplicationUser.builder()
                    .email(request.getPassword())
                    .username(request.getUsername())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .userRoles(request.getUserRoles())
                    .build());
            String token = jwtTokenProvider.generateToken(request.getUsername(), request.getUserRoles());
            return SignInResponse.builder().username(request.getUsername()).userRoles(request.getUserRoles()).accessToken(token).build();

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
