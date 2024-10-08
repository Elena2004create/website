package com.example.shop.controllers;

import com.example.shop.models.User;
import com.example.shop.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    /*@GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }


    @PostMapping("/registration")
    public String createUser(User user, Model model) {
        if (!userService.createUser(user)) {
            model.addAttribute("errorMessage", "Пользователь с email: " + user.getEmail() + " уже существует");
            return "registration";
        }
        return "redirect:/login";
    }

    @GetMapping("/hello")
    public String securityUrl() {
        return "hello";
    }

    @GetMapping("/user/{user}")
    public String userInfo(@PathVariable("user") User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("products", user.getProducts());
        return "user-info";
    }*/
    @PostMapping("/registration")
    public User createUser(@RequestBody User user) {
        if (!userService.createUser(user)) {
            //model.addAttribute("errorMessage", "Пользователь с email: " + user.getEmail() + " уже существует");
            return user;
        }
        return user;
    }

    @GetMapping("/user/{id}")
    public User userInfo(@PathVariable Long id) {
        return userService.findUserById(id);
    }

}
