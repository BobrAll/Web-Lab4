package com.bobr.WebLab4.controllers;

import com.bobr.WebLab4.dao.PointsDAO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    private final PointsDAO pointsDAO;

    public MainController(PointsDAO pointsDAO) {
        this.pointsDAO = pointsDAO;
    }

    @GetMapping("/main")
    public String mainPage(Model model) {
        model.addAttribute("points", pointsDAO.getPoints());
        return "main.html";
    }
}
