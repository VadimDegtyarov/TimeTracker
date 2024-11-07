package org.krainet.timetracker.controllers.PageControllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    @GetMapping("/user/login")
    public String getLoginPage() {
        return "Login-Page";
    }

}
