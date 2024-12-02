package com.hari.service;

import com.hari.helpers.AuthPassengerDetails;
import com.hari.model.Passenger;
import com.hari.repository.PassengerRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final PassengerRepository passengerRepository;

    public UserDetailsServiceImpl(PassengerRepository passengerRepository) {
        this.passengerRepository = passengerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Passenger> passenger= passengerRepository.findPassengerByEmail(email);
        if (passenger.isPresent()) {
            return new AuthPassengerDetails(passenger.get());
        } else {
            throw new UsernameNotFoundException("Cannot find Passenger by the give Email");
        }

    }
}
