package com.bobr.WebLab4.dao;

import com.bobr.WebLab4.models.Point;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

@Component
public class PointsDAO {
    private ArrayList<Point> points = new ArrayList<>();

    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        //Для теста
        points.add(new Point(1, 2, 3, true, LocalDateTime.now().format(formatter)));
        points.add(new Point(2, 2, 1, false, LocalDateTime.now().format(formatter)));
        points.add(new Point(4, 0, 2, true, LocalDateTime.now().format(formatter)));

    }
    public ArrayList<Point> getPoints() {
        return points;
    }
}
