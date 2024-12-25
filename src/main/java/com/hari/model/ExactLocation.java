package com.hari.model;


import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExactLocation extends BaseModel{
    private Double latitude;
    private Double longitude;
}
