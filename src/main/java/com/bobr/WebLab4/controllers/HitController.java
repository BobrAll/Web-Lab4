package com.bobr.WebLab4.controllers;

import com.bobr.WebLab4.beans.HitHandler;
import com.bobr.WebLab4.models.Hit;
import com.bobr.WebLab4.repos.HitsRepository;
import com.bobr.WebLab4.repos.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Transactional
@RestController
@RequestMapping("/hit")
public class HitController {
    HitsRepository hitsRepo;
    UserRepository userRepo;
    HitHandler hitHandler;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public HitController(HitsRepository hitsRepo, HitHandler hitHandler, UserRepository userRepo) {
        this.hitsRepo = hitsRepo;
        this.hitHandler = hitHandler;
        this.userRepo = userRepo;
    }

    @GetMapping
    public Iterable<Hit> getHits() {
        return hitsRepo.findAllByOwner(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    @PostMapping
    public void addHit(@RequestBody() Hit hit) {
        if (hitHandler.isValidCoordinates(hit))
        {
            hit.setDateTime(LocalDateTime.now().format(formatter));
            hit.setSuccess(hitHandler.isSuccess(hit));
            hit.setOwner(SecurityContextHolder.getContext().getAuthentication().getName());

            hitsRepo.save(hit);
        }
    }

    @DeleteMapping
    public void deleteAllHits() {
        hitsRepo.deleteAllByOwner(SecurityContextHolder.getContext().getAuthentication().getName());
    }
}
