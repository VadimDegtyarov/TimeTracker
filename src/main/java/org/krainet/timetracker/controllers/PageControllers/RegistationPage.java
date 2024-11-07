package org.krainet.timetracker.controllers.PageControllers;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.krainet.timetracker.Service.UserService;
import org.krainet.timetracker.dto.UserDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/registration")
@AllArgsConstructor
public class RegistationPage {

    private final UserService userService;

    @GetMapping("/1")
    public String showCreateUserForm() {


        return "Registration/1-email-and-pass";
    }

    @PostMapping("/save-info")
    public String handleStep1(@RequestParam String email, @RequestParam String password, HttpSession session) {

        session.setAttribute("email", email);
        session.setAttribute("password", password);


        return "redirect:/registration/2";
    }

    @GetMapping("/2")
    public String showStep2Form() {

        return "Registration/2-firstName-lastName";
    }

    @PostMapping("/create-user")
    public String create(@RequestParam String firstName, @RequestParam String lastName, HttpSession session) {

        String email = (String) session.getAttribute("email");
        String password = (String) session.getAttribute("password");
        UserDTO dto = new UserDTO();
        dto.setPassword(password);
        dto.setEmail(email);
        dto.setFirstName(firstName);
        dto.setLastName(lastName);
        userService.create(dto);
        session.invalidate();
        return "redirect:/";
    }


}
