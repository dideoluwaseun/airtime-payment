package com.oluwaseun.airtimepayment.service;

import com.oluwaseun.airtimepayment.dto.SignInResponse;
import com.oluwaseun.airtimepayment.dto.SignUpRequest;

import java.util.List;

public interface UserService {
    SignInResponse signIn(String username, String password);

    SignInResponse signup(SignUpRequest request);

    List<String> getUserRoles();
}
