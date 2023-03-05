package com.bobr.WebLab4.controllers;

import com.bobr.WebLab4.beans.HitHandler;
import com.bobr.WebLab4.models.Hit;
import com.bobr.WebLab4.repos.HitsRepo;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/hit")
public class HitController {
    HitsRepo hitsRepo;
    HitHandler hitHandler;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public HitController(HitsRepo hitsRepo, HitHandler hitHandler) {
        this.hitsRepo = hitsRepo;
        this.hitHandler = hitHandler;
    }

    @GetMapping
    public Iterable<Hit> getHits() {
        return hitsRepo.findAll();
    }

    @PostMapping
    public void addHit(@RequestBody() Hit hit) {
        if (hitHandler.isValidCoordinates(hit))
        {
            hit.setDateTime(LocalDateTime.now().format(formatter));
            hit.setSuccess(hitHandler.isSuccess(hit));

            hitsRepo.save(hit);
        }
    }

    @DeleteMapping
    public void deleteAllHits() {
        hitsRepo.deleteAll();
    }
}
