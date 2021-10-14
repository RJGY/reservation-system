package com.reservation_system.service;

import com.reservation_system.model.Amenity;
import com.reservation_system.repos.AmenityRepository;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class AmenityService {

    private final AmenityRepository amenityRepository;

    public AmenityService(final AmenityRepository amenityRepository) {
        this.amenityRepository = amenityRepository;
    }

    public List<Amenity> findAll() {
        return amenityRepository.findAll();
    }

    public Amenity get(final Long id) {
        return amenityRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Long create(final Amenity amenity) {
        return amenityRepository.save(amenity).getId();
    }

    public void update(final Long id, final Amenity amenity) {
        final Amenity existingAmenity = amenityRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        amenityRepository.save(amenity);
    }

    public void delete(final Long id) {
        amenityRepository.deleteById(id);
    }

}
