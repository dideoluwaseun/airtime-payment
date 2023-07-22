package com.oluwaseun.airtimepayment.dto;

import com.oluwaseun.airtimepayment.domain.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class UserAuthResponse {
    private String username;
    private List<UserRole> userRoles;
    private String accessToken;
}
