package com.oluwaseun.airtimepayment.service;

import com.oluwaseun.airtimepayment.dto.UserAuthResponse;
import com.oluwaseun.airtimepayment.dto.SignUpRequest;

import java.util.List;

public interface UserService {
    UserAuthResponse signIn(String username, String password);

    UserAuthResponse signup(SignUpRequest request);

    List<String> getUserRoles();
}
