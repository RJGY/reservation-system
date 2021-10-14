package com.reservation_system.repos;

import com.reservation_system.model.Amenity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AmenityRepository extends JpaRepository<Amenity, Long> {
}
