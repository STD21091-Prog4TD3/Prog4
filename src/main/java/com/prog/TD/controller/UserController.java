package com.prog.TD.controller;

import com.prog.TD.modele.UserEntity;
import com.prog.TD.repository.UserSessionRepository;
import com.prog.TD.service.AuthentificationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletResponse;

@Controller
public class UserController {
    private final AuthentificationService authenticationService;
    private final UserSessionRepository userSessionRepository;

    public UserController(AuthentificationService authenticationService, UserSessionRepository userSessionRepository) {
        this.authenticationService = authenticationService;
        this.userSessionRepository = userSessionRepository;
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("userEntity", new UserEntity());
        return "login";
    }

    @PostMapping("/login")
    public String authenticateUser(@ModelAttribute("userEntity")UserEntity userEntity, HttpServletResponse response) {
        if (authenticationService.authenticate(userEntity.getUserName(), userEntity.getPassword(), response)) {
            return "redirect:/";
        }
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpServletResponse response) {
        authenticationService.deleteCookie(response, "userSessionId");
        return "redirect:/login";
    }
}
