package com.oluwaseun.airtimepayment.controller;

import com.oluwaseun.airtimepayment.dto.PurchaseAirtimeRequest;
import com.oluwaseun.airtimepayment.dto.PurchaseAirtimeResponse;
import com.oluwaseun.airtimepayment.dto.SignInResponse;
import com.oluwaseun.airtimepayment.dto.SignUpRequest;
import com.oluwaseun.airtimepayment.service.AirtimeProductService;
import com.oluwaseun.airtimepayment.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserServiceImpl userService;

    @PostMapping("sign-in")
    public ResponseEntity<SignInResponse> signIn(@RequestParam String username, @RequestParam String password) {
        SignInResponse signInResponse = userService.signIn(username, password);
        return new ResponseEntity<>(signInResponse, HttpStatus.OK);
    }

    @PostMapping("sign-up")
    public ResponseEntity<SignInResponse> signUp(@RequestBody SignUpRequest signUpRequest) {
        SignInResponse signInResponse = userService.signup(signUpRequest);
        return new ResponseEntity<>(signInResponse, HttpStatus.OK);
    }
}
