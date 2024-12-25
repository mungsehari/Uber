package com.hari.service;

import com.hari.apis.LocationServiceApi;
import com.hari.apis.UberSocketApi;
import com.hari.dto.CreateBookingDto;
import com.hari.dto.DriverLocationDto;
import com.hari.dto.RideRequestDto;
import com.hari.model.Booking;
import com.hari.model.Driver;
import com.hari.model.Passenger;
import com.hari.repository.BookingRepository;
import com.hari.repository.DriverRepository;
import com.hari.repository.PassengerRepository;
import com.hari.request.NearbyDriversRequestDto;
import com.hari.request.UpdateBookingRequestDto;
import com.hari.response.CreateBookingResponseDto;
import com.hari.response.UpdateBookingResponseDto;
import com.hari.status.BookingStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class BookingServiceImpl implements BookingService {
    private final PassengerRepository passengerRepository;
    private final BookingRepository bookingRepository;

    private final RestTemplate restTemplate;

    private final LocationServiceApi locationServiceApi;

    private final UberSocketApi uberSocketApi;
    private final DriverRepository driverRepository;

    public BookingServiceImpl(PassengerRepository passengerRepository,
                              BookingRepository bookingRepository,
                              LocationServiceApi locationServiceApi,
                              UberSocketApi uberSocketApi,
                              DriverRepository driverRepository) {
        this.passengerRepository = passengerRepository;
        this.bookingRepository = bookingRepository;
        this.restTemplate = new RestTemplate();
        this.locationServiceApi = locationServiceApi;
        this.uberSocketApi = uberSocketApi;
        this.driverRepository = driverRepository;
    }


    @Override
    public CreateBookingResponseDto createBooking(CreateBookingDto bookingDetails) {
        Optional<Passenger> passenger = passengerRepository.findById(bookingDetails.getPassengerId());
        Booking booking = Booking.builder()

                .bookingStatus(BookingStatus.ASSIGNED_DRIVER)
                .startLocation(bookingDetails.getStartLocation())
//                .endLocation(bookingDetails.getEndLocation())
                .passenger(passenger.get())
                .build();
        Booking newBooking = bookingRepository.save(booking);

        // make an api call to location service to fetch nearby drivers

        NearbyDriversRequestDto request = NearbyDriversRequestDto.builder()
                .latitude(bookingDetails.getStartLocation().getLatitude())
                .longitude(bookingDetails.getStartLocation().getLongitude())
                .build();

        processNearbyDriversAsync(request, bookingDetails.getPassengerId(), newBooking.getId());

        return CreateBookingResponseDto.builder()
                .bookingId(newBooking.getId())
                .bookingStatus(newBooking.getBookingStatus().toString())
                .build();
    }

    @Override
    public UpdateBookingResponseDto updateBooking(UpdateBookingRequestDto bookingRequestDto, Long bookingId) {
        System.out.println(bookingRequestDto.getDriverId().get());
        Optional<Driver> driver = driverRepository.findById(bookingRequestDto.getDriverId().get());
        // TODO : if(driver.isPresent() && driver.get().isAvailable())
        bookingRepository.updateBookingStatusAndDriverById(bookingId, BookingStatus.SCHEDULED,driver.get());
        // TODO: driverRepository.update -> make it unavailable
        Optional<Booking> booking = bookingRepository.findById(bookingId);
        return UpdateBookingResponseDto.builder()
                .bookingId(bookingId)
                .status(booking.get().getBookingStatus())
                .driver(Optional.ofNullable(booking.get().getDriver()))
                .build();
    }


    private void processNearbyDriversAsync(NearbyDriversRequestDto requestDto, Long passengerId, Long bookingId) {
        Call<DriverLocationDto[]> call = locationServiceApi.getNearbyDrivers(requestDto);
        System.out.println(call.request().url() + " " + call.request().method() + " " + call.request().headers());

        call.enqueue(new Callback<DriverLocationDto[]>() {

                         @Override
                         public void onResponse(Call<DriverLocationDto[]> call, Response<DriverLocationDto[]> response) {
                             if(response.isSuccessful() && response.body() != null) {
                                 List<DriverLocationDto> driverLocations = Arrays.asList(response.body());
                                 driverLocations.forEach(driverLocationDto -> {
                                     System.out.println(driverLocationDto.getDriverId() + " " + "lat: " + driverLocationDto.getLatitude() + "long: " + driverLocationDto.getLongitude());
                                 });

                                 try {
                                     raiseRideRequestAsync(RideRequestDto.builder().passengerId(passengerId).bookingId(bookingId).build());
                                 } catch (IOException e) {
                                     throw new RuntimeException(e);
                                 }

                             } else {
                                 System.out.println("Request failed" + response.message());
                             }
                         }


                         @Override
                         public void onFailure(Call<DriverLocationDto[]> call, Throwable throwable) {
                             throwable.printStackTrace();
                         }
                     }
        );
    }
    private void raiseRideRequestAsync(RideRequestDto requestDto) throws IOException {
        Call<Boolean> call = uberSocketApi.raiseRideRequest(requestDto);

        System.out.println(call.request().url() + " " + call.request().method() + " " + call.request().headers());
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                System.out.println(response.isSuccessful());
                System.out.println(response.message());
                if (response.isSuccessful() && response.body() != null) {
                    Boolean result = response.body();
                    System.out.println("Driver response is" + result.toString());

                } else {
                    System.out.println("Request for ride failed" + response.message());
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}

