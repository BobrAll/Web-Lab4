package com.bobr.WebLab4.repos;

import com.bobr.WebLab4.models.Point;
import org.springframework.data.repository.CrudRepository;

public interface PointsRepo extends CrudRepository<Point, Long> {
}
