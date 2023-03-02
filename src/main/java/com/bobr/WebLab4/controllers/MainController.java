package com.bobr.WebLab4.controllers;

import com.bobr.WebLab4.beans.HitHandler;
import com.bobr.WebLab4.models.Point;
import com.bobr.WebLab4.repos.PointsRepo;
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
    private final PointsRepo pointsRepo;
    private final HitHandler hitHandler;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public MainController(HitHandler hitHandler, PointsRepo pointsRepo) {
        this.hitHandler = hitHandler;
        this.pointsRepo = pointsRepo;
    }

    @GetMapping("")
    public String mainPage(@RequestParam(name = "x", required = false) Double x,
                           @RequestParam(name = "y", required = false) Double y,
                           @RequestParam(name = "r", required = false) Double r,
                           Model model) {
        if (x != null && y != null && r != null)
        {
            String dateTime = LocalDateTime.now().format(formatter);
            pointsRepo.save(new Point(x, y, r, hitHandler.isHit(x, y, r), dateTime));
        }

        model.addAttribute("points", pointsRepo.findAll());
        return "/main.html";
    }

    @GetMapping("/clear")
    public String deletePoints() {
        pointsRepo.deleteAll();
        return "/main.html";
    }
}
