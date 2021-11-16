package com.reservation_system;

import com.reservation_system.model.*;
import com.reservation_system.repos.AmenityRepository;
import com.reservation_system.repos.CapacityRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.CommandLineRunner;
import com.reservation_system.repos.ReservationRepository;
import com.reservation_system.repos.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class ReservationSystemApplication {

    private Map<AmenityType, Integer> initialCapacities =
        new HashMap<>() {
            {
                put(AmenityType.GYM, 20);
                put(AmenityType.POOL, 4);
                put(AmenityType.SAUNA, 1);
            }
        };

    public static void main(String[] args) {
        SpringApplication.run(ReservationSystemApplication.class, args);
    }

    @Bean
    public CommandLineRunner loadData(UserRepository userRepository,
                                      CapacityRepository capacityRepository,
                                      AmenityRepository amenityRepository) {
        return (args) -> {
            User user = User.builder()
                    .fullName("Reese Gunardi")
                    .username("RJGY")
                    .passwordHash(bCryptPasswordEncoder().encode("12345"))
                    .build();
            userRepository.save(user);
            Amenity amenityPool = Amenity.builder()
                    .amenityType(AmenityType.POOL)
                    .build();
            Amenity amenitySauna = Amenity.builder()
                    .amenityType(AmenityType.SAUNA)
                    .build();
            Amenity amenityGym = Amenity.builder()
                    .amenityType(AmenityType.GYM)
                    .build();
            amenityRepository.save(amenityPool);
            amenityRepository.save(amenitySauna);
            amenityRepository.save(amenityGym);
            for (AmenityType amenityType : initialCapacities.keySet()) {
                capacityRepository.save(new Capacity(amenityType, initialCapacities.get(amenityType)));
            }
        };
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
