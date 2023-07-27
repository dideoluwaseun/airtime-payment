package com.oluwaseun.airtimepayment.dto;

import com.oluwaseun.airtimepayment.domain.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class SignUpRequest {
    @NotBlank(message = "username is required")
    @Pattern(regexp = "^(.+)@(.+)$", message = "username must be a valid email address ")
    private String username;
    @NotEmpty(message = "at least one user role is required")
    private List<UserRole> userRoles;
    @NotBlank(message = "password is required")
    private String password;
}
