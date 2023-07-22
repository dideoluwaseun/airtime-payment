package com.oluwaseun.airtimepayment.service.impl;

import com.github.dockerjava.api.exception.UnauthorizedException;
import com.oluwaseun.airtimepayment.Exception.DuplicateEntityException;
import com.oluwaseun.airtimepayment.domain.ApplicationUser;
import com.oluwaseun.airtimepayment.dto.SignUpRequest;
import com.oluwaseun.airtimepayment.repository.UserRepository;
import com.oluwaseun.airtimepayment.security.JwtTokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

import static com.oluwaseun.airtimepayment.domain.UserRole.ROLE_ADMIN;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class UserServiceImplTest {

    @Mock
    private  UserRepository userRepository;
    @Mock
    private  PasswordEncoder passwordEncoder;
    @Mock
    private  JwtTokenProvider jwtTokenProvider;
    @Mock
    private  AuthenticationManager authenticationManager;

    @InjectMocks
    private UserServiceImpl userService;

    @Nested
    class SignIn {
        @Test
        void signIn() {
            //when
            when(userRepository.findByUsername(anyString())).thenReturn(ApplicationUser.builder()
                    .username("user@gmail.com").userRoles(List.of(ROLE_ADMIN)).build());

            Authentication authentication = new UsernamePasswordAuthenticationToken("user@gmail.com", "password");
            when(authenticationManager.authenticate(any())).thenReturn(authentication);

            String token = "asdfghjk45678";
            when(jwtTokenProvider.generateToken(anyString(), anyList())).thenReturn(token);

            //then
            assertThatCode(() -> userService.signIn("user@gmail.com", "password")).doesNotThrowAnyException();
        }

        @Test
        void catchException() {
            //when
            when(userRepository.findByUsername(anyString())).thenReturn(ApplicationUser.builder()
                    .username("user@gmail.com").userRoles(List.of(ROLE_ADMIN)).build());

            when(authenticationManager.authenticate(any())).thenThrow(new BadCredentialsException("incorrect password"));

            //then
            assertThrows(UnauthorizedException.class, () -> userService.signIn("user@gmail.com", "password"));
        }
    }

    @Nested
    class SignUp {
        SignUpRequest request;

        @BeforeEach
        void initObject() {
            //given
            request = SignUpRequest.builder()
                    .username("user@gmail.com")
                    .password("password")
                    .userRoles(Arrays.asList(ROLE_ADMIN))
                    .build();
        }

        @Test
        void signup() {
            //when
            when(userRepository.existsByUsername(anyString())).thenReturn(false);

            //then
            assertThatCode(() -> userService.signup(request)).doesNotThrowAnyException();
        }

        @Test
        void catchException() {
            //when
            when(userRepository.existsByUsername(anyString())).thenReturn(true);

            //then
            assertThrows(DuplicateEntityException.class, () -> userService.signup(request));
        }
    }
}