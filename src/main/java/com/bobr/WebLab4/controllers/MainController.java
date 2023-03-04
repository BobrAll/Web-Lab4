package com.bobr.WebLab4.controllers;

import com.bobr.WebLab4.beans.HitHandler;
import com.bobr.WebLab4.models.Hit;
import com.bobr.WebLab4.repos.HitsRepo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("/main")
public class MainController {
    private final HitsRepo hitsRepo;
    private final HitHandler hitHandler;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public MainController(HitHandler hitHandler, HitsRepo hitsRepo) {
        this.hitHandler = hitHandler;
        this.hitsRepo = hitsRepo;
    }

    @GetMapping("")
    public String mainPage(@RequestParam(name = "x", required = false) Double x,
                           @RequestParam(name = "y", required = false) Double y,
                           @RequestParam(name = "r", required = false) Double r,
                           Model model) {
        if (hitHandler.isValidCoordinates(x, y, r))
        {
            String dateTime = LocalDateTime.now().format(formatter);
            hitsRepo.save(new Hit(x, y, r, hitHandler.isHit(x, y, r), dateTime));
        }

        model.addAttribute("hits", hitsRepo.findAll());
        return "/main.html";
    }

    @GetMapping("/clear")
    public String deleteHits() {
        hitsRepo.deleteAll();
        return "/main.html";
    }
}
