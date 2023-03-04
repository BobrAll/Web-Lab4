package com.bobr.WebLab4.beans;

import com.bobr.WebLab4.models.Hit;
import org.springframework.stereotype.Component;

@Component
public class HitHandler {
    private final static double minX = -4;
    private static final double maxX = 4;
    private final static double minY = -4;
    private static final double maxY = 4;
    private final static double minR = -4;
    private static final double maxR = 4;

    public boolean isHit(Hit hit) {
        double x = hit.getX();
        double y = hit.getY();
        double r = hit.getR();

        if (x < 0 && y > 0)
            return (x >= -r && y <= r);
        else if (x > 0 && y > 0)
            return y < r / 2 && y < r / 2 - x / 2;
        else if (x > 0 && y < 0)
            return (x * x + y * y < r * r);
        else
            return false;
    }
    public boolean isValidCoordinates(Hit hit) {
        if (hit.getX() == null || hit.getY() == null || hit.getR() == null)
            return false;

        return (hit.getX() >= minX && hit.getX() <= maxX &&
                hit.getY() >= minY && hit.getY() <= maxY &&
                hit.getR() >= minR && hit.getR() <= maxR);
    }
}
