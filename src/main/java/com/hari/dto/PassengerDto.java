package com.hari.dto;

import java.util.Date;

import com.hari.model.Passenger;

import lombok.*;


@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PassengerDto {

    private String id;
    private String name;
    private String email;
    private String password;
    private String phoneName;
    private Date createAt;


    public static PassengerDto from(Passenger p) {

        PassengerDto result = PassengerDto.builder()
                .id(p.getId().toString())
                .name(p.getName())
                .createAt(p.getCreatedAt())
                .email(p.getEmail())
                .password(p.getPassword())
                .phoneName(p.getPhoneNumber())
                .build();
        return result;
    }
}
