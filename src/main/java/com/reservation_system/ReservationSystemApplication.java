package com.reservation_system;

import com.reservation_system.repos.AmenityRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.CommandLineRunner;
import com.reservation_system.model.Amenity;
import com.reservation_system.model.Reservation;
import com.reservation_system.model.User;
import com.reservation_system.repos.ReservationRepository;
import com.reservation_system.repos.UserRepository;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

@SpringBootApplication
public class ReservationSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReservationSystemApplication.class, args);
    }

    @Bean
    public CommandLineRunner loadData(UserRepository userRepository,
                                      ReservationRepository reservationRepository,
                                      AmenityRepository amenityRepository) {
        return (args) -> {
            User user = userRepository.save(new User());
            Amenity amenity = amenityRepository.save(new Amenity());
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date date = new Date();
            LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            Reservation reservation = Reservation.builder()
                    .reservationDate(localDate)
                    .startTime(LocalTime.of(12, 00))
                    .endTime(LocalTime.of(13, 00))
                    .userReservation(user)
                    .reservationAmenity(amenity)
                    .build();

            reservationRepository.save(reservation);
        };
    }
}
