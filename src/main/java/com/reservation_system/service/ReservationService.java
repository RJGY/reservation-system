package com.reservation_system.service;

import com.reservation_system.domain.Amenity;
import com.reservation_system.domain.Reservation;
import com.reservation_system.domain.User;
import com.reservation_system.model.ReservationDTO;
import com.reservation_system.repos.AmenityRepository;
import com.reservation_system.repos.ReservationRepository;
import com.reservation_system.repos.UserRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final AmenityRepository amenityRepository;

    public ReservationService(final ReservationRepository reservationRepository,
            final UserRepository userRepository, final AmenityRepository amenityRepository) {
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
        this.amenityRepository = amenityRepository;
    }

    public List<ReservationDTO> findAll() {
        return reservationRepository.findAll()
                .stream()
                .map(reservation -> mapToDTO(reservation, new ReservationDTO()))
                .collect(Collectors.toList());
    }

    public ReservationDTO get(final Long id) {
        return reservationRepository.findById(id)
                .map(reservation -> mapToDTO(reservation, new ReservationDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Long create(final ReservationDTO reservationDTO) {
        final Reservation reservation = new Reservation();
        mapToEntity(reservationDTO, reservation);
        return reservationRepository.save(reservation).getId();
    }

    public void update(final Long id, final ReservationDTO reservationDTO) {
        final Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(reservationDTO, reservation);
        reservationRepository.save(reservation);
    }

    public void delete(final Long id) {
        reservationRepository.deleteById(id);
    }

    private ReservationDTO mapToDTO(final Reservation reservation,
            final ReservationDTO reservationDTO) {
        reservationDTO.setId(reservation.getId());
        reservationDTO.setReservationDate(reservation.getReservationDate());
        reservationDTO.setStartTime(reservation.getStartTime());
        reservationDTO.setEndTime(reservation.getEndTime());
        reservationDTO.setUserReservation(reservation.getUserReservation() == null ? null : reservation.getUserReservation().getId());
        reservationDTO.setReservationAmenity(reservation.getReservationAmenity() == null ? null : reservation.getReservationAmenity().getId());
        return reservationDTO;
    }

    private Reservation mapToEntity(final ReservationDTO reservationDTO,
            final Reservation reservation) {
        reservation.setReservationDate(reservationDTO.getReservationDate());
        reservation.setStartTime(reservationDTO.getStartTime());
        reservation.setEndTime(reservationDTO.getEndTime());
        if (reservationDTO.getUserReservation() != null && (reservation.getUserReservation() == null || !reservation.getUserReservation().getId().equals(reservationDTO.getUserReservation()))) {
            final User userReservation = userRepository.findById(reservationDTO.getUserReservation())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "userReservation not found"));
            reservation.setUserReservation(userReservation);
        }
        if (reservationDTO.getReservationAmenity() != null && (reservation.getReservationAmenity() == null || !reservation.getReservationAmenity().getId().equals(reservationDTO.getReservationAmenity()))) {
            final Amenity reservationAmenity = amenityRepository.findById(reservationDTO.getReservationAmenity())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "reservationAmenity not found"));
            reservation.setReservationAmenity(reservationAmenity);
        }
        return reservation;
    }

}
