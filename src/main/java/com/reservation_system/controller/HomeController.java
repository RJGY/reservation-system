package com.reservation_system.controller;

import com.reservation_system.model.Amenity;
import com.reservation_system.model.Reservation;
import com.reservation_system.model.User;
import com.reservation_system.service.AmenityService;
import com.reservation_system.service.ReservationService;
import com.reservation_system.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpSession;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;


@Controller
public class HomeController {

    final UserService userService;
    final ReservationService reservationService;
    final AmenityService amenityService;

    public HomeController(UserService userService, ReservationService reservationService, AmenityService amenityService) {
        this.userService = userService;
        this.reservationService = reservationService;
        this.amenityService = amenityService;
    }

    @GetMapping("/")
    public String index(Model model) {
        return "index";
    }

    @GetMapping("/reservations")
    public String reservations(Model model, HttpSession session) {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String name = principal.getUsername();
        User user = userService.getUserByUsername(name);

        // This should always be the case
        if (user != null) {
            session.setAttribute("user", user);

            // Empty reservation object in case the user creates a new reservation
            Reservation reservation = new Reservation();
            model.addAttribute("reservation", reservation);

            return "reservations";
        }

        return "index";
    }

    @PostMapping("/reservations-submit")
    public String reservationsSubmit(@ModelAttribute Reservation reservation,
                                     @SessionAttribute("user") User user) {

        // Save to DB after updating
        assert user != null;
        reservation.setUserReservation(user);
        List<Amenity> amenities = amenityService.findAll();
        for (Amenity amenity : amenities) {
            if (amenity.getAmenityType() == reservation.getReservationAmenity().getAmenityType()) {
                Set<Reservation> amenityReservations = amenity.getReservationAmenity();
                amenityReservations.add(reservation);
                amenity.setReservationAmenity(amenityReservations);
                reservation.setReservationAmenity(amenity);
                break;
            }
        }
        reservationService.create(reservation);
        Set<Reservation> userReservations = user.getReservations();
        userReservations.add(reservation);
        user.setReservations(userReservations);
        userService.update(user.getId(), user);
        amenityService.update(reservation.getReservationAmenity().getId(), reservation.getReservationAmenity());
        return "redirect:/reservations";
    }


}
