package org.krainet.timetracker.controllers.PageControllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.krainet.timetracker.Service.ProjectService;

import org.krainet.timetracker.Service.UserService;
import org.krainet.timetracker.model.Project;
import org.krainet.timetracker.model.User;

import org.springframework.security.core.annotation.AuthenticationPrincipal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


import java.security.Principal;
import java.util.List;

@Controller
@AllArgsConstructor
@Slf4j
public class MainPage {
    private final ProjectService projectService;
    private final UserService userService;

    @RequestMapping("/")
    public String mainPage(Model model, Principal principal) {


        log.info("Список проектов пользователя: {}", principal);

        User user = userService.findByUserEmail(principal.getName()).orElseThrow();
        List<Project> projectList = projectService.readAll().stream()
                .filter(project -> project.getUser().getEmail().equals(principal.getName()))
                .toList();

        model.addAttribute("projects", projectList);
        model.addAttribute("user", user);
        return "MainPage";
    }
}
