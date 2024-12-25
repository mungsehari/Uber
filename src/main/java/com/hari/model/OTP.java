package com.hari.model;


import jakarta.persistence.Entity;
import lombok.*;

import java.util.Random;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OTP extends BaseModel {
    private String code;

    private String sentToNumber;

    public static OTP make(String phoneNumber) {

        // initialize a Random object somewhere; you should only need one
        Random random = new Random();

        // generate a random integer from 0 to 899, then add 100
        Integer code = random.nextInt(9000) + 1000;

        return OTP.builder().code(code.toString()).sentToNumber(phoneNumber).build();
    }

}
