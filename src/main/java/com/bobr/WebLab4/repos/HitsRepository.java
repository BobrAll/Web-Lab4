package com.bobr.WebLab4.repos;

import com.bobr.WebLab4.models.Hit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HitsRepository extends JpaRepository<Hit, Long> {
    Optional<Hit> deleteAllByOwner(String owner);
    Iterable<Hit> findAllByOwner(String owner);
}
