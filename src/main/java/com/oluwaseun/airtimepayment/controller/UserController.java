package com.oluwaseun.airtimepayment.controller;

import com.oluwaseun.airtimepayment.dto.SignInResponse;
import com.oluwaseun.airtimepayment.dto.SignUpRequest;
import com.oluwaseun.airtimepayment.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserServiceImpl userService;

    //endpoint for user sign in, returns jwt access token
    @PostMapping("sign-in")
    public ResponseEntity<SignInResponse> signIn(@RequestParam String username, @RequestParam String password) {
        SignInResponse userAuthResponse = userService.signIn(username, password);
        return new ResponseEntity<>(userAuthResponse, HttpStatus.OK);
    }

    //endpoint to sign up new user
    @PostMapping("sign-up")
    public ResponseEntity<String> signUp(@Valid @RequestBody SignUpRequest signUpRequest) {
        userService.signup(signUpRequest);
        return new ResponseEntity<>("user created successfully", HttpStatus.CREATED);
    }
}
