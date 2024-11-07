package org.krainet.timetracker.controllers;

import lombok.AllArgsConstructor;
import org.krainet.timetracker.Exception.AppError;
import org.krainet.timetracker.Exception.GlobalExceptionHandler;
import org.krainet.timetracker.Exception.ResourceNotFoundException;
import org.krainet.timetracker.Service.UserService;
import org.krainet.timetracker.dto.UserDTO;
import org.krainet.timetracker.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/update")
    public String updateUser(@ModelAttribute UserDTO userDto) {

        userService.create(userDto);
        User user =userService.findByUserEmail(userDto.getEmail()).orElseThrow();
        return "redirect:/users/" + user.getId();
    }

    @PostMapping("/update-password")
    public String updatePassword(@RequestParam Long userId,
                                 @RequestParam String oldPassword,
                                 @RequestParam String newPassword,
                                 @RequestParam String confirmPassword) {
        if (newPassword.equals(confirmPassword)) {
            User user = userService.findById(userId).orElseThrow();
            if (oldPassword.equals(user.getPassword())) {
                user.setPassword(newPassword);
                userService.update(user);
            } else {
                return new ResourceNotFoundException("Ошибка").getMessage();
            }


        }
        return "redirect:/users/" + userId;
    }


    @GetMapping("/{id}")
    public String getUserById(@PathVariable Long id, Model model) {
        User user = userService.findById(id).orElseThrow();
        model.addAttribute("user", user);
        return "user-details";
    }


    @GetMapping("/")
    public String getAllUsers(Model model) {
        model.addAttribute("users", userService.readAll());
        return "user-list";
    }

    @PutMapping
    public ResponseEntity<User> update(@RequestBody User user) {
        return new ResponseEntity<>(userService.update(user), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public HttpStatus delete(@PathVariable Long id) {
        userService.delete(id);
        return HttpStatus.OK;
    }

}
