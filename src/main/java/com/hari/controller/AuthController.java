package com.hari.controller;

import com.hari.config.JwtProvider;
import com.hari.request.AuthRequest;
import com.hari.response.AuthResponse;
import com.hari.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.hari.dto.PassengerDto;
import com.hari.request.PassengerSignUpRequestDto;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Value("${cookie.expiry}")
    private int cookieExpiry;
    private final AuthService authService;

    private  final AuthenticationManager authenticationManager;

    public AuthController(AuthService authService, AuthenticationManager authenticationManager) {
        this.authService = authService;
        this.authenticationManager = authenticationManager;


    }



    @PostMapping("/signup/passenger")
    public ResponseEntity<PassengerDto> singUp(@RequestBody PassengerSignUpRequestDto requestDto) {
      PassengerDto response=  authService.signupPassenger(requestDto);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/signin/passenger")
    public ResponseEntity<?> signIn(@RequestBody AuthRequest request, HttpServletResponse response){
        Authentication authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword()));
        if (authentication.isAuthenticated()) {

            String token = JwtProvider.generateToken(authentication);
            ResponseCookie cookie=ResponseCookie.from("jwt",token)
                            .httpOnly(true)
                                    .secure(false)
                                            .path("/")
                                            .maxAge(cookieExpiry)
                    .build();
            response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());


            return new ResponseEntity<>(token, HttpStatus.OK);
        }

        return new ResponseEntity<>(AuthResponse.builder().success(false).build(), HttpStatus.UNAUTHORIZED);


    }


}
