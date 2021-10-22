package com.reservation_system.controller;

import com.reservation_system.model.User;
import com.reservation_system.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HomeController {

    final UserService userService;

    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String index(Model model) {
        return "index";
    }

    @GetMapping("/reservations")
    public String reservations(Model model) {
        User user = userService.get(10000L);
        model.addAttribute("user", user);

        return "reservations";
    }
}
