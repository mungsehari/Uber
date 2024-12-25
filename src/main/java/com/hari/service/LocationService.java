package com.hari.service;

import com.hari.dto.DriverLocationDto;

import java.util.List;

public interface LocationService {
    Boolean saveDriverLocation(String driverId, Double latitude, Double Longitude);

    List<DriverLocationDto> getNearByDrivers(Double latitude, Double Longitude);
}
