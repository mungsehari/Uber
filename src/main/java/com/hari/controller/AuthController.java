package com.hari.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hari.model.Passenger;
import com.hari.request.PassengerSignUpRequestDto;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @PostMapping("/singup/passenger")
    public ResponseEntity<Passenger> singUp(@RequestBody PassengerSignUpRequestDto requestDto) {
        // Implement logic for signing up a passenger
        return null;
    }

}
