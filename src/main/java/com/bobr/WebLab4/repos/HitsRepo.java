package com.bobr.WebLab4.repos;

import com.bobr.WebLab4.models.Hit;
import org.springframework.data.repository.CrudRepository;

public interface HitsRepo extends CrudRepository<Hit, Long> {
}
