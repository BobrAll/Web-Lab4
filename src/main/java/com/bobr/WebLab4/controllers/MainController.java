package com.bobr.WebLab4.controllers;

import com.bobr.WebLab4.repos.UserRepository;
import com.bobr.WebLab4.security.SecurityUserDetailsService;
import com.bobr.WebLab4.models.User;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
@AllArgsConstructor
public class MainController {
    private UserRepository userRepo;
    private PasswordEncoder passwordEncoder;
    private SecurityUserDetailsService userDetailsManager;

    @GetMapping("/")
    public String defaultPage() {
        return "redirect:main";
    }

    @GetMapping(value = "/main")
    public String main() {
        return "main";
    }

    @GetMapping("/login")
    public String login() {
        if (isAuthenticated())
            return "redirect:main";
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        if (isAuthenticated())
            return "redirect:main";
        return "register";
    }

    @PostMapping("/register")
    public String addUser(@RequestParam Map<String, String> body) {
        if (!userRepo.findUserByUsername(body.get("username")).isEmpty())
            return "redirect:register";

        User user = new User();
        user.setUsername(body.get("username"));
        user.setPassword(passwordEncoder.encode(body.get("password")));
        user.setAccountNonLocked(true);
        userDetailsManager.createUser(user);
        return "redirect:login";
    }

    private boolean isAuthenticated() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return !(auth == null || AnonymousAuthenticationToken.class.isAssignableFrom(auth.getClass()));
    }
}
