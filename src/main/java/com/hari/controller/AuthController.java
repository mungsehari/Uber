package com.hari.controller;

import com.hari.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hari.dto.PassengerDto;
import com.hari.request.PassengerSignUpRequestDto;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }



    @PostMapping("/signup/passenger")
    public ResponseEntity<PassengerDto> singUp(@RequestBody PassengerSignUpRequestDto requestDto) {
      PassengerDto response=  authService.signupPassenger(requestDto);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
