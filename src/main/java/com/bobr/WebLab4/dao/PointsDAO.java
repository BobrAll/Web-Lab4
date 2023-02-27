package com.bobr.WebLab4.dao;

import com.bobr.WebLab4.models.Point;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

@Component
public class PointsDAO {
    private ArrayList<Point> points = new ArrayList<>();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public ArrayList<Point> getPoints() {
        return points;
    }
    public void addPoint(Point point) {
        points.add(point);
    }
    public void deletePoints() {
        points = new ArrayList<>();
    }
}
