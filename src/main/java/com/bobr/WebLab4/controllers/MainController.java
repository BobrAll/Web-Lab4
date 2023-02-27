package com.bobr.WebLab4.controllers;

import com.bobr.WebLab4.beans.HitHandler;
import com.bobr.WebLab4.dao.PointsDAO;
import com.bobr.WebLab4.models.Point;
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
    private final PointsDAO pointsDAO;
    private final HitHandler hitHandler;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public MainController(PointsDAO pointsDAO, HitHandler hitHandler) {
        this.pointsDAO = pointsDAO;
        this.hitHandler = hitHandler;
    }

    @GetMapping("")
    public String mainPage(@RequestParam(name = "x", required = false) Double x,
                           @RequestParam(name = "y", required = false) Double y,
                           @RequestParam(name = "r", required = false) Double r,
                           Model model) {
        if (x != null && y != null && r != null)
        {
            String dateTime = LocalDateTime.now().format(formatter);
            pointsDAO.addPoint(new Point(x, y, r, hitHandler.isHit(x, y, r), dateTime));
        }

        model.addAttribute("points", pointsDAO.getPoints());
        return "/main.html";
    }

    @GetMapping("/clear")
    public String deletePoints() {
        pointsDAO.deletePoints();
        return "/main.html";
    }
}
