package com.oluwaseun.airtimepayment.service.impl;

import com.oluwaseun.airtimepayment.domain.ApplicationUser;
import com.oluwaseun.airtimepayment.repository.UserRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static com.oluwaseun.airtimepayment.domain.UserRole.ROLE_ADMIN;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class UserDetailsServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @Nested
    class LoadUserByUserName {
        @Test
        void loadUserByUsername() {
            //when
            when(userRepository.findByUsername(anyString())).thenReturn(ApplicationUser.builder()
                    .username("user@gmail.com").userRoles(List.of(ROLE_ADMIN)).password("encodedpassword").build());

            //then
            assertThatCode(() -> userDetailsService.loadUserByUsername("user@gmail.com")).doesNotThrowAnyException();
        }

        @Test
        void throwsException() {
            //when
            when(userRepository.findByUsername(anyString())).thenReturn(null);

            //then
            assertThrows(UsernameNotFoundException.class, () -> userDetailsService.loadUserByUsername("user@gmail.com"));
        }
    }
}