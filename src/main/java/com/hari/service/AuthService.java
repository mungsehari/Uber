package com.hari.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.hari.dto.PassengerDto;
import com.hari.model.Passenger;
import com.hari.repository.PassengerRepository;
import com.hari.request.PassengerSignUpRequestDto;

@Service
public class AuthService {

    private final PassengerRepository passengerRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public AuthService(PassengerRepository passengerRepository,BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.passengerRepository = passengerRepository;
        this.bCryptPasswordEncoder=bCryptPasswordEncoder;
    }

    public PassengerDto signupPassenger(PassengerSignUpRequestDto passengerSignUpRequestDto) {
        Passenger passenger = Passenger.builder()
                .email(passengerSignUpRequestDto.getEmail())
                .name(passengerSignUpRequestDto.getName())
                .password(bCryptPasswordEncoder.encode(passengerSignUpRequestDto.getPassword()))
                .phoneName(passengerSignUpRequestDto.getPhoneName())
                .build();

        Passenger newPassenger = passengerRepository.save(passenger);
       return PassengerDto.from(newPassenger);


    }

}
