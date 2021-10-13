package com.reservation_system.rest;

import com.reservation_system.model.AmenityDTO;
import com.reservation_system.service.AmenityService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/amenitys", produces = MediaType.APPLICATION_JSON_VALUE)
public class AmenityController {

    private final AmenityService amenityService;

    public AmenityController(final AmenityService amenityService) {
        this.amenityService = amenityService;
    }

    @GetMapping
    public ResponseEntity<List<AmenityDTO>> getAllAmenitys() {
        return ResponseEntity.ok(amenityService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AmenityDTO> getAmenity(@PathVariable final Long id) {
        return ResponseEntity.ok(amenityService.get(id));
    }

    @PostMapping
    public ResponseEntity<Long> createAmenity(@RequestBody @Valid final AmenityDTO amenityDTO) {
        return new ResponseEntity<>(amenityService.create(amenityDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateAmenity(@PathVariable final Long id,
            @RequestBody @Valid final AmenityDTO amenityDTO) {
        amenityService.update(id, amenityDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAmenity(@PathVariable final Long id) {
        amenityService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
