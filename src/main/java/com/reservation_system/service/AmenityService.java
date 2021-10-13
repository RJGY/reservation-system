package com.reservation_system.service;

import com.reservation_system.domain.Amenity;
import com.reservation_system.model.AmenityDTO;
import com.reservation_system.repos.AmenityRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class AmenityService {

    private final AmenityRepository amenityRepository;

    public AmenityService(final AmenityRepository amenityRepository) {
        this.amenityRepository = amenityRepository;
    }

    public List<AmenityDTO> findAll() {
        return amenityRepository.findAll()
                .stream()
                .map(amenity -> mapToDTO(amenity, new AmenityDTO()))
                .collect(Collectors.toList());
    }

    public AmenityDTO get(final Long id) {
        return amenityRepository.findById(id)
                .map(amenity -> mapToDTO(amenity, new AmenityDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Long create(final AmenityDTO amenityDTO) {
        final Amenity amenity = new Amenity();
        mapToEntity(amenityDTO, amenity);
        return amenityRepository.save(amenity).getId();
    }

    public void update(final Long id, final AmenityDTO amenityDTO) {
        final Amenity amenity = amenityRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(amenityDTO, amenity);
        amenityRepository.save(amenity);
    }

    public void delete(final Long id) {
        amenityRepository.deleteById(id);
    }

    private AmenityDTO mapToDTO(final Amenity amenity, final AmenityDTO amenityDTO) {
        amenityDTO.setId(amenity.getId());
        amenityDTO.setAmenityType(amenity.getAmenityType());
        return amenityDTO;
    }

    private Amenity mapToEntity(final AmenityDTO amenityDTO, final Amenity amenity) {
        amenity.setAmenityType(amenityDTO.getAmenityType());
        return amenity;
    }

}
